import unittest
import zserio

from testutils import getZserioApi

class ExpressionFullTemplateArgumentTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.api = getZserioApi(__file__, "templates.zs").expression_full_template_argument

    def testReadWrite(self):
        colorInternal = self.api.FullTemplateArgument_Color_7C6F461F(False, 10)
        self.assertTrue(colorInternal.isExpressionFieldUsed())

        colorExternal = (self.api.FullTemplateArgument_Color_6066EE71(False, 10))
        self.assertFalse(colorExternal.isExpressionFieldUsed())

        fullTemplateArgumentHolder = self.api.FullTemplateArgumentHolder(colorInternal, colorExternal)
        writer = zserio.BitStreamWriter()
        fullTemplateArgumentHolder.write(writer)
        reader = zserio.BitStreamReader(writer.getByteArray())
        readFullTemplateArgumentHolder = self.api.FullTemplateArgumentHolder()
        readFullTemplateArgumentHolder.read(reader)
        self.assertEqual(fullTemplateArgumentHolder, readFullTemplateArgumentHolder)
