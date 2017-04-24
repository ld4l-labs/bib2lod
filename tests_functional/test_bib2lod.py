"""Functional tasts for bib2lod Java program."""
from .testcase_with_tmpdir import TestCase
import glob
import io
import json
import os.path
from rdiffb import RDiffB
import subprocess


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


def example_config():
    """Read example configuration file, return object to modify."""
    return json.load(open('src/main/resources/example.config.json', 'r'))


class TestBib2lod(TestCase):
    """Some bib2lod tests..."""

    def write_config(self, config, filename="config.json"):
        """Write test config to tmpdir, return filepath."""
        filepath = os.path.join(self.tmpdir, filename)
        with open(filepath, 'w') as fh:
            json.dump(config, fh)
        return(filepath)

    def test01_config_errors(self):
        """Test simple errors attempting to run bib2lod."""
        out = run_bib2lod([])
        self.assertIn(b"You must provide a value after '-c'", out)
        out = run_bib2lod(['DOES_NOT_EXIST'])
        self.assertIn(b"Configuration file does not exist", out)
        out = run_bib2lod(['tests_functional/testdata/bad1.json'])
        self.assertIn(b"invalid JSON", out)

    def test02_cornell_ld4l_conversion(self):
        """Test Cornel LD4L conversions based on sample configuration file."""
        indirs = 'sample-data/sample-conversions/marcxml-to-ld4l/cornell'
        outdir = self.tmpdir
        for indir in glob.glob(os.path.join(indirs, '*')):
            # FIXME - should look for *.xml in each dir and then build tests on that
            src = os.path.join(indir, '102063.min.xml')
            ref = os.path.join(indir, '102063.min.ttl')
            dst = os.path.join(outdir, '102063.min.nt')
            config = example_config()
            config['InputService']['source'] = src
            config['OutputService']['destination'] = outdir
            config_filepath = self.write_config(config)
            out = run_bib2lod([config_filepath])
            self.assertTrue(os.path.exists(dst))
            self.assertEqual(RDiffB(['data.ld4l.org/cornell']).compare_files([ref, dst]), 0)
