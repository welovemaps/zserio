import unittest
import os
import apsw

from testutils import getZserioApi, getApiDir

class RowIdAndWithoutRowIdTablesTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.api = getZserioApi(__file__, "sql_without_rowid_tables.zs").rowid_and_without_rowid_tables
        cls._fileName = os.path.join(getApiDir(os.path.dirname(__file__)),
                                     "rowid_and_without_rowid_tables_test.sqlite")

    def setUp(self):
        if os.path.exists(self._fileName):
            os.remove(self._fileName)
        self._database = self.api.RowIdAndWithoutRowIdDb.from_file(self._fileName)

    def tearDown(self):
        self._database.close()

    def testRowIdColumn(self):
        self._database.create_schema()
        self.assertFalse(self._isColumnInTable("rowid", self.WITHOUT_ROWID_TABLE_NAME))
        self.assertTrue(self._isColumnInTable("rowid", self.ORDINARY_ROWID_TABLE_NAME))

    def testCreateOrdinaryRowIdTable(self):
        testTable = self._database.without_row_id_table
        testTable.create_ordinary_rowid_table()
        self.assertTrue(self._isColumnInTable("rowid", self.WITHOUT_ROWID_TABLE_NAME))

    def testWithoutRowIdTableNamesBlackList(self):
        self._database.create_schema([self.WITHOUT_ROWID_TABLE_NAME])
        self.assertTrue(self._isColumnInTable("rowid", self.WITHOUT_ROWID_TABLE_NAME))
        self.assertTrue(self._isColumnInTable("rowid", self.ORDINARY_ROWID_TABLE_NAME))

    def _isColumnInTable(self, columnName, tableName):
        sqlQuery = "SELECT " + columnName + " FROM " + tableName + " LIMIT 0"

        # try select to check if hidden column exists
        try:
            self._database.connection.cursor().execute(sqlQuery)
            return True
        except apsw.SQLError:
            return False

    WITHOUT_ROWID_TABLE_NAME = "withoutRowIdTable"
    ORDINARY_ROWID_TABLE_NAME = "ordinaryRowIdTable"
