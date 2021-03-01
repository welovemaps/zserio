import unittest

from zserio.bitposition import (alignto, bits_to_bytes, bytes_to_bits, PythonRuntimeException)

class BitPositionTest(unittest.TestCase):

    def test_alignto(self):
        bitposition = 5
        self.assertEqual(5, alignto(0, bitposition))
        self.assertEqual(5, alignto(1, bitposition))
        self.assertEqual(6, alignto(2, bitposition))
        self.assertEqual(6, alignto(3, bitposition))
        self.assertEqual(8, alignto(4, bitposition))
        self.assertEqual(5, alignto(5, bitposition))
        self.assertEqual(6, alignto(6, bitposition))
        self.assertEqual(7, alignto(7, bitposition))
        self.assertEqual(8, alignto(8, bitposition))

    def test_bits_to_bytes(self):
        self.assertEqual(1, bits_to_bytes(8))
        self.assertEqual(3, bits_to_bytes(24))
        with self.assertRaises(PythonRuntimeException):
            bits_to_bytes(4)
        with self.assertRaises(PythonRuntimeException):
            bits_to_bytes(9)

    def test_bytes_to_bits(self):
        self.assertEqual(0, bytes_to_bits(0))
        self.assertEqual(8, bytes_to_bits(1))
        self.assertEqual(16, bytes_to_bits(2))
