import unittest
import zserio

from testutils import getZserioApi

class EmptyStructureTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.api = getZserioApi(__file__, "structure_types.zs").empty_structure_with_parameter

    def testParamConstructor(self):
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(1)
        self.assertEqual(1, emptyStructureWithParameter.param)

    def testFromReader(self):
        param = 1
        reader = zserio.BitStreamReader([])
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter.from_reader(reader, param)
        self.assertEqual(param, emptyStructureWithParameter.param)
        self.assertEqual(0, emptyStructureWithParameter.bitsizeof())

    def testEq(self):
        emptyStructureWithParameter1 = self.api.EmptyStructureWithParameter(1)
        emptyStructureWithParameter2 = self.api.EmptyStructureWithParameter(1)
        emptyStructureWithParameter3 = self.api.EmptyStructureWithParameter(0)
        self.assertTrue(emptyStructureWithParameter1 == emptyStructureWithParameter2)
        self.assertFalse(emptyStructureWithParameter1 == emptyStructureWithParameter3)

    def testHash(self):
        emptyStructureWithParameter1 = self.api.EmptyStructureWithParameter(1)
        emptyStructureWithParameter2 = self.api.EmptyStructureWithParameter(1)
        emptyStructureWithParameter3 = self.api.EmptyStructureWithParameter(0)
        self.assertEqual(hash(emptyStructureWithParameter1), hash(emptyStructureWithParameter2))
        self.assertTrue(hash(emptyStructureWithParameter1) != hash(emptyStructureWithParameter3))

    def testGetParam(self):
        param = 1
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(param)
        self.assertEqual(param, emptyStructureWithParameter.param)

    def testBitSizeOf(self):
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(1)
        self.assertEqual(0, emptyStructureWithParameter.bitsizeof(1))

    def testInitializeOffsets(self):
        bitPosition = 1
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(1)
        self.assertEqual(bitPosition, emptyStructureWithParameter.initialize_offsets(bitPosition))

    def testRead(self):
        param = 1
        reader = zserio.BitStreamReader([])
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(param)
        emptyStructureWithParameter.read(reader)
        self.assertEqual(param, emptyStructureWithParameter.param)
        self.assertEqual(0, emptyStructureWithParameter.bitsizeof())

    def testWrite(self):
        param = 1
        writer = zserio.BitStreamWriter()
        emptyStructureWithParameter = self.api.EmptyStructureWithParameter(param)
        emptyStructureWithParameter.write(writer)
        byteArray = writer.byte_array
        self.assertEqual(0, len(byteArray))
        reader = zserio.BitStreamReader(writer.byte_array, writer.bitposition)
        readEmptyStructureWithParameter = self.api.EmptyStructureWithParameter.from_reader(reader, param)
        self.assertEqual(emptyStructureWithParameter, readEmptyStructureWithParameter)
