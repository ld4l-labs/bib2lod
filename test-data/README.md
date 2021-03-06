# Test Data and Functional Testing

## Organization of test data

Within the test-data directory, the top-level subdirectories specify an input and output format combination (e.g., marcxml-to-biblioteko). Below the top level, files and directories can be organized as desired. 

Test files consist of a pair of files with the same base name and extensions corresponding to the file contents. One file contains the test input and the other file contains the expected converter output from the input file.

For example, `102063.min.xml` and `102063.min.ttl` represent a pair of test files.

## Functional testing

### Arguments

Functional tests should accept three arguments:

`-s` Input source: a path to either a directory or a file. A directory name may or may not end in a slash. A filename must have an extension.

`-i` Test input file extension. If the path specifies an input file, this should be omitted, and if present will be ignored.

`-o` Expected output file extension.

`-d` Output directory to use instead of temporary directory (with automatic cleanup). If an output directory is specified then a time-stamped sub-directory will be created and converter output will not be removed after the tests.

`-b` Bnode patterns to ignore (default is any triples starting `http://data.ld4l.org/`, may be repeated for multiple patterns)


#### Examples

`$ ./testconv.py -s test-data/marcxml-to-biblioteko/cornell/ -i xml -o ttl`

`$ ./testconv.py -s test-data/marcxml-to-biblioteko/cornell/102063-min -i xml -o ttl`

`$ ./testconv.py -s test-data/marcxml-to-biblioteko/cornell/102063-min/102063.min.xml -o ttl`

#### Input location

* Directory: Walk the directory tree starting from the specified directory. The test should be executed on any pair of test files (as defined above) encountered. 
* File: Execute the test using the corresponding expected output file in the same directory.
 
## bib2lod configuration

  * For each test the code will look for a config file named `config.json` in the current directory or a parent directory (working up from the current directory). If none is found then `test-data/config.json` will be used.
  * The test makes the following commandline substitutions to the config values:
    * `InputService:class=org.ld4l.bib2lod.io.FileInputService`
    * `InputService:source=<test input parameter>`
    * `InputService:extension=<test extension parameter>`
    * `OutputService:class=org.ld4l.bib2lod.io.FileOutputService`
    * `OutputService:destination=<test output location>`
    * `OutputService:format=N-TRIPLES`

### Error conditions

The program should terminate and log an error to stderr in the following cases:

* Config file not found or unreadable
* Directory input: directory not found or unreadable
* File input: file not found or unreadable

### Logging

#### Warnings

A warning should be logged to stdout and execution should continue in the following cases:

* Input file found with no corresponding output file
* bib2lod terminates with an error

#### Results

The following results should be logged to stdout:

* Total number of files tested
* Number of passing files 
* Names of failing files (as part of log of tests run)

