# ph-codelists

Code lists that are too big or too general for an enum, or that are used by more than one project.
Every list comes with its English names and a language fall back: translated names are looked up from the
most specific to the most general locale, and the English name is the last resort.

| Module | Content |
|--------|---------|
| `ph-codelists-unit` | The unit codes of UN/ECE Recommendation N°20 (units of measure) and N°21 (packaging), 2162 codes |

## ph-codelists-unit

```java
IUnitCode aUnit = UnitCodes.getFromIDOrNull ("KGM");
aUnit.getID ();                          // "KGM"
aUnit.getDisplayName ();                 // "kilogram" - English, as published
aUnit.getDisplayText (Locale.GERMAN);    // the German name if there is one, otherwise "kilogram"
UnitCodes.containsID ("C62");            // true
```

* **Source of the English names:** the sheet `Unit` of the EN 16931 code list workbook (currently
  `EN16931 code lists values v17b - used from 2026-05-15`) of the
  [Registry of supporting artefacts to implement EN 16931](https://ec.europa.eu/digital-building-blocks/sites/spaces/DIGITAL/pages/467108974/Registry+of+supporting+artefacts+to+implement+EN16931).
  It is the list BT-130 and BT-150 of EN 16931 refer to and merges Rec 20 (1756 codes) and Rec 21
  (406 codes). `src/etc/create-unit-codes.py` regenerates `codelists/unece-units.tsv` from a new
  workbook.
* **Why no enum:** the static initializer of an enum with 2162 constants exceeds the 64 KB limit of the JVM.
  The data are read lazily from the TSV resource instead.
* **German names:** `ph-codelists-unit_de.properties` contains a German name for each of the 2162 codes. UN/ECE
  publishes the lists in English only, so these names are **machine translated** from the English names and have not
  been reviewed by a native speaker. Corrections are welcome as contributions.
* **Other languages:** add `ph-codelists-unit_<locale>.properties` (UTF-8, key is the code) to the classpath, in
  your project or as a contribution to this one. Any locale without a bundle falls back to the English name.

# Building

Requires Java 17 or higher.

```bash
mvn clean install
```

# News and noteworthy

v1.0.0 - work in progress
* Initial version with `ph-codelists-unit`, including machine translated German names
