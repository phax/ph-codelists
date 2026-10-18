# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Code lists that do not belong into a single project: they are too big for an enum, or several projects
(en16931-basics, phlox, ...) need them. Multi module Maven project, parent `ph-codelists-parent-pom`.

Rules of the house:
* One module per code list family; the module has no dependency besides `ph-commons`.
* English names are the data. Other languages are optional resource bundles
  `<module>_<locale>.properties` (UTF-8, key = code) resolved with `LocaleHelper.getCalculatedLocaleListForResolving`,
  with the English name as the last fall back.
* Never invent translations. A translation needs a source that can be named in the README.
* The data files are generated from their source (see `src/etc`); do not edit them by hand.

## Build & Test Commands

```bash
mvn clean install               # Build and test all modules
mvn test -pl ph-codelists-unit -Dtest=UnitCodesTest
mvn license:format              # Apply the license header of src/etc/license-template.txt
```

Requires Java 17+.

## Architecture

```
ph-codelists-unit  com.helger.codelists.unit
  IUnitCode / UnitCode   one code: ID, source recommendation, English name, getDisplayText (Locale)
  EUnitCodeSource        REC20, REC21
  UnitCodes              lazy registry read from codelists/unece-units.tsv
```
