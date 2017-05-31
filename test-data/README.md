# Test Data and Functional Testing

## Organization of test data

Within the test-data directory, the top-level subdirectories specify an input and output format combination (e.g., marcxml-to-biblioteko). Below the top level, files and directories can be organized as desired. 

Test files consist of a pair of files with the same base name and extensions corresponding to the file contents. One file contains the test input and the other file contains the expected converter output from the input file.

For example, `102063.min.xml` and `102063.min.ttl` represent a pair of test files.

## Functional testing

### Arguments

Functional and integration tests should accept three arguments:

`-p` Path relative to the test-data directory of either a directory or a file. A directory name may or may not end in a slash. A filename must have an extension.

`-i` Test input file extension. If the path specifies an input file, this should be omitted, and if present will be ignored.

`-o` Test output file extension


#### Examples

`$ test -p marcxml-to-biblioteko/cornell/ -i xml -o ttl`

`$ test -p marcxml-to-biblioteko/cornell/102063-min -i xml -o ttl`

`$ test -p marcxml-to-biblioteko/cornell/102063-min/102063.min.xml -o ttl`

#### Interpretation of path argument

* Directory: Recurse the directory tree starting from the specified directory. The test should be executed on any pair of test files (as defined above) encountered along the way. 
* File: Execute the test on the corresponding test output file in the same directory.

### Error conditions

The program should halt execution and log an error to stderr in the following cases:

* Required arguments missing
* Directory input: directory not found or unreadable
* File input: file not found or unreadable

### Logging

#### Warnings

A warning should be logged to stdout and execution should continue in the following cases:

* Input file found with no corresponding output file

#### Results

The following results should be logged to stdout:

* Total number of files tested
* Number of files passing the test
* Names of files failing the test, identifying the cause of error in some way [TBD]

