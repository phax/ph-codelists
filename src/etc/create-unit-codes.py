#!/usr/bin/env python3
# Creates ph-codelists-unit/src/main/resources/codelists/unece-units.tsv from the sheet "Unit" of the
# EN 16931 code list workbook (columns: Source, Code, Name). Only needs the Python standard library.
#
#   python3 src/etc/create-unit-codes.py "EN16931 code lists values v17b - used from 2026-05-15.xlsx"
import re
import sys
import zipfile
import xml.etree.ElementTree as ET

NS = {'m': 'http://schemas.openxmlformats.org/spreadsheetml/2006/main',
      'r': 'http://schemas.openxmlformats.org/officeDocument/2006/relationships'}
OUT = 'ph-codelists-unit/src/main/resources/codelists/unece-units.tsv'


def main(path):
    z = zipfile.ZipFile(path)
    wb = ET.fromstring(z.read('xl/workbook.xml'))
    rels = {r.get('Id'): r.get('Target') for r in ET.fromstring(z.read('xl/_rels/workbook.xml.rels'))}
    strings = [''.join(t.text or '' for t in si.iter('{%s}t' % NS['m']))
               for si in ET.fromstring(z.read('xl/sharedStrings.xml'))]
    target = None
    for s in wb.find('m:sheets', NS):
        if s.get('name') == 'Unit':
            target = rels[s.get('{%s}id' % NS['r'])]
    if target is None:
        sys.exit('No sheet "Unit" in ' + path)
    root = ET.fromstring(z.read('xl/' + target.replace('/xl/', '').lstrip('/')))
    out = ['source\tcode\tname']
    for n, row in enumerate(root.iter('{%s}row' % NS['m'])):
        if n == 0:
            continue  # header
        cells = {}
        for c in row:
            v = c.find('m:v', NS)
            if v is not None:
                cells[re.match(r'[A-Z]+', c.get('r')).group()] = strings[int(v.text)] if c.get('t') == 's' else v.text
        out.append('%s\t%s\t%s' % (cells['A'].lower(), cells['B'].strip(), cells['C'].strip()))
    with open(OUT, 'w', encoding='utf-8') as f:
        f.write('\n'.join(out) + '\n')
    print(len(out) - 1, 'codes written to', OUT)


if __name__ == '__main__':
    main(sys.argv[1])
