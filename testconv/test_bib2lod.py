"""Functional tasts for bib2lod Java program."""
import unittest
from .conversion_tester import run_bib2lod, ConversionTester


class TestBib2lod(unittest.TestCase):
    """Some bib2lod tests..."""

    def test01_config_errors(self):
        """Test simple errors attempting to run bib2lod."""
        out = run_bib2lod([])
        self.assertIn(b"You must provide a parameter after '-c'", out)
        out = run_bib2lod(['DOES_NOT_EXIST'])
        self.assertIn(b"Configuration file does not exist", out)
        out = run_bib2lod(['testconv/testdata/bad1.json'])
        self.assertIn(b"invalid JSON", out)

    def test02_conversions(self):
        """Test conversions under test-data."""
        tester = ConversionTester()
        tester.run_conversions('test-data', 'xml', 'ttl')
        self.assertEqual(tester.num_fail, 0)
