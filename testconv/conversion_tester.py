"""Conversion Tester."""

import logging
import os
import os.path
import subprocess
from rdiffb import RDiffB
import tempfile
import shutil
import time
import datetime


def run_bib2lod(args):
    """Run bib2lod with specified args.

    First argument must be the configuration file name.
    """
    prefix_args = ['java', '-jar', 'target/bib2lod.jar', '-c']
    prefix_args.reverse()
    for arg in prefix_args:
        args.insert(0, arg)
    proc = subprocess.Popen(args, stdout=subprocess.PIPE)
    (out, err) = proc.communicate()
    return(out)


class ConversionTester(object):

    def __init__(self, bnodes=[], outdir=None):
        """Initialize tester and create tempdir."""
        self.bnodes = bnodes
        self.outdir = outdir
        self.num_pass = 0
        self.num_fail = 0
        self.tmpdir = None
        self.default_config = 'test-data/config.json'
        if (self.outdir):
            if (not os.path.isdir(self.outdir)):
                raise Exception("Output directory specified does not exist!")
            # Make timestamped subdir
            timestamp = datetime.datetime.fromtimestamp(
                time.time()).strftime('%Y-%m-%dT%H:%M:%S')
            self.outdir = os.path.join(self.outdir, timestamp)
            os.mkdir(self.outdir)
            logging.warn("Writing output files to %s" % (self.outdir))
        else:
            self.tmpdir = tempfile.mkdtemp()
            self.outdir = self.tmpdir
            if (not os.path.isdir(self.tmpdir)):
                raise Exception("Failed to create tempdir for tests!")

    def __del__(self):
        """Clean up tempdir if one was created."""
        if (self.tmpdir):
            if (not os.path.isdir(self.tmpdir)):
                raise Exception("Ooops, no tempdir (%s) to clean up?"
                                % (self.tmpdir))
            shutil.rmtree(self.tmpdir)

    @property
    def num_tests(self):
        """Number of tests is total of pass and fails."""
        return(self.num_pass + self.num_fail)

    def check_conversion(self, src, ref):
        """Check a single conversion."""
        config_filepath = self.find_config(src)
        outdir = self.outdir
        (root, infile) = os.path.split(src)
        (root2, reffile) = os.path.split(ref)
        if (os.path.exists(ref)):
            context = "Test %s: %s -> %s" % (root, infile, reffile)
            # Can't control output file name in bib2lod config,
            # it will be the input file name with extension changed
            # to that defined by the output format, in our case we
            # assume '.nt'
            (src_base, src_ext) = os.path.splitext(infile)
            outfile = src_base + '.nt'
            dst = os.path.join(outdir, outfile)
            out = run_bib2lod(
                [config_filepath,
                 '--set', 'InputService:class='
                          'org.ld4l.bib2lod.io.FileInputService',
                 '--set', 'InputService:source=' + src,
                 '--set', 'InputService:extension=' + src_ext,
                 '--set', 'OutputService:class='
                          'org.ld4l.bib2lod.io.FileOutputService',
                 '--set', 'OutputService:destination=' + outdir,
                 '--set', 'OutputService:format=N-TRIPLES'])
            if (not os.path.exists(dst)):
                logging.error("%s - FAIL\n"
                              "Output file %s does not exist" %
                              (context, dst))
                self.num_fail += 1
            elif (RDiffB(self.bnodes).compare_files([ref, dst]) != 0):
                logging.error("%s - FAIL\n"
                              "Files %s and %s have different graphs" %
                              (context, ref, dst))
                self.num_fail += 1
            else:
                self.num_pass += 1
                logging.warn("%s - PASS" % (context))
        else:
            logging.warn("Input %s: %s has no ref output file %s" %
                         (root, infile, reffile))

    def find_config(self, src):
        """Find configuration file for src, else return default.

        FIXME - could cache result here rather than repeating search
        each time for same directory.
        """
        head = src
        while True:
            (head, tail) = os.path.split(head)
            conf = os.path.join(head, 'config.json')
            if (os.path.isfile(conf)):
                logging.info("Using config %s" % (conf))
                return(conf)
            elif (not head):
                logging.info("Using default config %s" %
                             (self.default_config))
                return(self.default_config)

    def run_conversion(self, src, ref_ext):
        """Run a single bib2lod conversions."""
        (base, ext) = os.path.splitext(src)
        ref = base + ref_ext
        self.check_conversion(src, ref)

    def run_conversions(self, start_dir, input_ext, ref_ext):
        """Run a set of bib2lod conversions under start_dir.

        Looks for tests that are pairs of files with input_ext and output_ext.
        """
        outdir = '/tmp'
        for root, dirs, files in os.walk(start_dir):
            logging.info("Looking for tests in %s" % (root))
            for infile in files:
                (base, ext) = os.path.splitext(infile)
                if (ext == input_ext):
                    reffile = base + ref_ext
                    ref = os.path.join(root, reffile)
                    src = os.path.join(root, infile)
                    self.check_conversion(src, ref)
