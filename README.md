# bib2lod

[![Build Status](https://travis-ci.org/ld4l-labs/bib2lod.svg?branch=develop)](https://travis-ci.org/ld4l-labs/bib2lod)
[![Coverage Status](https://coveralls.io/repos/github/ld4l-labs/bib2lod/badge.svg?branch=develop)](https://coveralls.io/github/ld4l-labs/bib2lod)

## Converts bibliographic records to RDF.

## News
* **2017-03-31** Release 0.1 pushed to `master`.
  * Converts a minimal MARCXML record to N-TRIPLES.
  * Most of the architecture is in place.

## Build
* Clone the respository from [https://github.com/ld4l-labs/bib2lod]()
* run `mvn install`
* Copy the executable jar from `target/bib2lod.jar` to your preferred work location.
* Copy the example configuration file from `src/main/resources/example.config.json` to your preferred work location. Rename it appropriately. For example, `first.config.json`.

## Configure
* Edit the configuration file to set appropriate input source and output destination.
* Within `InputService`, change the `source` attribute to point either to a single file of MARCXML, or to a directory containing MARCXML files.
  * Each input file must have a filename extension of `.xml`
* Within `OutputService`, change the `destination` attribute to point to your desired output directory. 
  * _You **must** create this directory before running the program._

## Run
* Execute the jar file, referencing the configuration file on the command line:
  * `java -jar bib2lod.jar -c first.config.json`
* Output will be written to the directory specified in the configuration file. 
  * One output file will be created for each input file. 
  * The name of the output file will be the same as the corresponding input file, but the extension will be `.nt`.
* A log directory will be created as `target/logs` in your work location directory. 
  * A log file of the run will be created as `target/logs/bib2log.log`
  * An existing log file will not be overwritten, but will be renamed with a timestamp, such as `bib2lod-2017-03-31-14-38-47-1.log`
