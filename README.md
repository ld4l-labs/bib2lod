# bib2lod

[![Build Status](https://travis-ci.org/ld4l-labs/bib2lod.svg?branch=develop)](https://travis-ci.org/ld4l-labs/bib2lod)
[![Coverage Status](https://coveralls.io/repos/github/ld4l-labs/bib2lod/badge.svg?branch=develop)](https://coveralls.io/github/ld4l-labs/bib2lod)

## What is bib2lod?

bib2lod is a full record MARC-to-bibliotek-o converter that will:  

* Accept any valid MARC record (or set of valid MARC records) as input. 
* Convert each input record to RDF in the [bibliotek-o framework](https://github.com/ld4l-labs/bibliotek-o). The bibliotek-o framework includes:
  * The [bibliotek-o ontology](https://github.com/ld4l-labs/bibliotek-o) (an extension to [BIBFRAME](https://www.loc.gov/bibframe/)).
  * Defined fragments of BIBFRAME and other external ontologies.
  * An application profile specifying rules for bibliographic metadata modeling using these ontologies.
  * Mappings from MARC to the bibliotek-o target ontologies against which the converter is being developed are in progress by the LD4L Labs/LD4P ontology mapping group.
* Convert all fields except local (9xx) fields to RDF.

In its initial implementation, it:

* Converts each record as a self-contained unit, with no attempt to reconcile URIs either locally across an entire data set or to external URIs. New URIs are minted using the configured local namespace and a random local name generator.
* Supports only file IO.


## News
* **2017-03-31** Release 0.1 pushed to `master`.
  * Converts a minimal MARCXML record to N-TRIPLES.
  * Most of the architecture is in place.
  
## Quick Start
* `git clone git@github.com:ld4l-labs/bib2lod.git`
* `cd bib2lod`
* `mvn install`
* `mkdir output`
* `java -jar target/bib2lod.jar -c src/main/resources/example.config.json`
* `more output/102063.min.nt`
  
## Build
* Clone the repository from [https://github.com/ld4l-labs/bib2lod]()
* run `mvn install`
* Copy the executable jar from `target/bib2lod.jar` to your preferred work location.
* Copy the example configuration file from `src/main/resources/example.config.json` to your preferred work location. Rename it appropriately. For example, `first.config.json`.

## Configure
* Edit the configuration file to set appropriate input source and output destination.
* Within `InputService`, change the `source` attribute to point either to a single file of MARCXML, or to a directory containing MARCXML files.
  * Each input file must have a filename extension of `.xml`
  * Sample minimal record is in `sample-data/marcxml-to-ld4l/cornell/102063-min/102063.min.xml`.
* Within `OutputService`, change the `destination` attribute to point to your desired output directory. 
  * _You **must** create this directory before running the program._

## Run
* Execute the jar file, referencing the configuration file on the command line:
  * `java -jar bib2lod.jar -c first.config.json`
* Output will be written in N-TRIPLE format to the directory specified in the configuration file. 
  * One output file will be created for each input file. 
  * The name of the output file will be the same as the corresponding input file, but the extension will be `.nt`.
* A log directory will be created as `target/logs` in your work location directory. 
  * A log file of the run will be created as `target/logs/bib2lod.log`
  * An existing log file will not be overwritten, but will be renamed with a timestamp, such as `bib2lod-2017-03-31-14-38-47-1.log`
  
### Command line options
As illustrated above, the command to run the converter looks like this:

    java -jar bib2lod.jar [options]
    
Where options are:

* -c __path__, --config __path__
  * Specify the path to the configuration file. 
    The path may be relative or absolute.
* -a __spec=value__, --add __spec=value__
  * Add a value to the configuration at the specified location. 
    For example, if the configuration file contains this section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES"
            },
    Then this command line option

            --add OutputService:destination=./output
    will have this effect on the relevant section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES",
              "destination": "./output"
            },
    Note that `--add` cannot be used to replace an existing value 
    in the configuration file. It will merely add an additional 
    value at the same location. To replace an existing value, 
    use `--set`.
    
* -d __spec__, --drop __spec__
  * Remove a value from the configuration at the specified location.
    For example, if the configuration file contains this section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES"
              "destination": "./output"
            },
    Then this command line option

            --drop OutputService:destination
    will have this effect on the relevant section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES",
            },
    If there are multiple values at the specified location, 
    `--drop` will remove all of them. If there are no such values,
    `--drop` will have no effect.

* -s __spec=value__, --set __spec=value__
  * Set a value into the configuration at the specified location,
    replacing any existing value. For example, if the 
    configuration file contains this section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES"
              "destination": "./output"
            },
    Then this command line option

            --set OutputService:destination=newOutput
    will have this effect on the relevant section:

            "OutputService": {
              "class": "org.ld4l.bib2lod.io.FileOutputService",
              "format": "N-TRIPLES",
              "destination": "newOutput"
            },
    If there are multiple values at the specified location, 
    `--set` will replace all of them. If there are no such values,
    `--set` proceeds like `--add`.
