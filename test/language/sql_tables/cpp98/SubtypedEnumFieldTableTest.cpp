#include <cstdio>
#include <string>
#include <fstream>

#include "gtest/gtest.h"

#include "sql_tables/TestDb.h"

namespace sql_tables
{
namespace subtyped_enum_field_table
{

class SubtypedEnumFieldTableTest : public ::testing::Test
{
public:
    SubtypedEnumFieldTableTest()
    {
        std::remove(DB_FILE_NAME);

        m_database = new sql_tables::TestDb(DB_FILE_NAME);
        m_database->createSchema();
    }

    ~SubtypedEnumFieldTableTest()
    {
        delete m_database;
    }

protected:
    static void fillRow(SubtypedEnumFieldTableRow& row, size_t i)
    {
        row.setId(static_cast<int32_t>(i));
        row.setEnumField(i % 3 == 0
                ? TestEnum::ONE
                : i % 3 == 1
                        ? TestEnum::TWO
                        : TestEnum::THREE);
    }

    static void fillRows(std::vector<SubtypedEnumFieldTableRow>& rows)
    {
        rows.clear();
        for (size_t i = 0; i < NUM_ROWS; ++i)
        {
            SubtypedEnumFieldTableRow row;
            fillRow(row, i);
            rows.push_back(row);
        }
    }

    static void checkRow(const SubtypedEnumFieldTableRow& row1, const SubtypedEnumFieldTableRow& row2)
    {
        ASSERT_EQ(row1.getId(), row2.getId());
        ASSERT_EQ(row1.getEnumField(), row2.getEnumField());
    }

    static void checkRows(const std::vector<SubtypedEnumFieldTableRow>& rows1,
            const std::vector<SubtypedEnumFieldTableRow>& rows2)
    {
        ASSERT_EQ(rows1.size(), rows2.size());
        for (size_t i = 0; i < rows1.size(); ++i)
            checkRow(rows1[i], rows2[i]);
    }

    static const char DB_FILE_NAME[];
    static const size_t NUM_ROWS;

    sql_tables::TestDb* m_database;
};

const char SubtypedEnumFieldTableTest::DB_FILE_NAME[] = "subtyped_enum_field_table_test.sqlite";
const size_t SubtypedEnumFieldTableTest::NUM_ROWS = 5;

TEST_F(SubtypedEnumFieldTableTest, readWithoutCondition)
{
    SubtypedEnumFieldTable& table = m_database->getSubtypedEnumFieldTable();

    std::vector<SubtypedEnumFieldTableRow> rows;
    fillRows(rows);
    table.write(rows);

    std::vector<SubtypedEnumFieldTableRow> readRows;
    table.read(readRows);
    checkRows(rows, readRows);
}

} // namespace subtyped_enum_field_table
} // namespace sql_tables
