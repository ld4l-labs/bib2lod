#!/usr/bin/env python
"""Conversion test client for bib2lod."""

import logging
import optparse
import os.path
import sys

from testconv.conversion_tester import ConversionTester


def main():
    """Command line test client."""
    if (sys.version_info < (2, 6)):
        sys.exit("This program requires python version 2.6 or later")

    # Options and arguments
    p = optparse.OptionParser(description='bib2lod conversion tests',
                              usage='usage: %prog [options] (-h for help)')

    p.add_option('--source', '-s', default='test-data',
                 help="input source: a path relative to to either a directory "
                      "or a file (default %default).")
    p.add_option('--input-extension', '-i', default='xml',
                 help="test input file extension. If the --source specifies "
                      "an input file, this should be omitted, and if present "
                      "will be ignored (default %default).")
    p.add_option('--output-directory', '-d',
                 help="output directory to use instead of temporary directory "
                      "(with automatic cleanup). If an output directory is "
                      "specified then a time-stamped sub-directory will be "
                      "created and converter output will not be removed after "
                      "the tests.")
    p.add_option('--output-format', '-f', default='TURTLE',
                 help="output serialization (default %default). " 
                 "Valid values: 'N-TRIPLES', 'TURTLE'.")
    p.add_option('--bnode', '-b', action='append', default=[],
                 help="add a regex for URIs that should be treated like "
                      "bnodes in diffs (will default to http://data.ld4l.org/ "
                      "if no value specified, repeatable)")
    p.add_option('--verbose', '-v', action='store_true',
                 help="verbose, show additional informational messages")
    p.add_option('--quiet', '-q', action='store_true',
                 help="only error output")

    (opt, args) = p.parse_args()
    if (len(opt.bnode) == 0):
        opt.bnode.append('http://data.ld4l.org/')
    if (len(opt.output_format) == 0):
        opt.output_format = 'TURTLE'

    # use --verbose & --debug to set up logging level
    level = (logging.ERROR if opt.quiet
             else logging.INFO if opt.verbose
             else logging.WARN)
    logging.basicConfig(level=level, format='%(message)s')

    tester = ConversionTester(bnodes=opt.bnode, outdir=opt.output_directory, 
            out_format=opt.output_format)
    if (os.path.isfile(opt.source)):
        # Special case of single input file specified
        tester.run_conversion(opt.source)
    else:
        # Treat source as if directory...
        tester.run_conversions(opt.source,
                               '.' + opt.input_extension)
    logging.warn("%d CONVERSIONS TESTED: %d PASSED, %d FAILED" %
                 (tester.num_tests, tester.num_pass, tester.num_fail))

    # Exit status: 0 if no failures
    sys.exit(tester.num_fail)


if __name__ == '__main__':
    main()
