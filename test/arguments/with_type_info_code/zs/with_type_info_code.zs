package with_type_info_code;

struct SimpleStruct
{
    uint32 fieldU32;
    string fieldString;
};

struct ComplexStruct
{
    optional SimpleStruct simpleStruct;
    uint32 array[] : lengthof(array) > 0;
    int:5 arrayWithLen[array[0]] if array[0] > 0;
    optional ParameterizedStruct(simpleStruct) paramStructArray[];
    bit<simpleStruct.fieldU32> dynamicBitField;
    packed bit<simpleStruct.fieldU32 + dynamicBitField> dynamicBitFieldArray[];

    function uint32 firstArrayElement()
    {
        return lengthof(array) > 0 ? array[0] : 0;
    }
};

struct ParameterizedStruct(SimpleStruct simple)
{
    uint8 array[simple.fieldU32];
};

subtype uint16 EnumUnderlyingType;

enum EnumUnderlyingType TestEnum
{
    One,
    TWO = 5,
    ItemThree
};

bitmask bit<10> TestBitmask
{
    RED,
    Green,
    ColorBlue
};

union SimpleUnion
{
    TestBitmask testBitmask;
    SimpleStruct simpleStruct;

    function uint32 simpleStructFieldU32()
    {
        return simpleStruct.fieldU32;
    }
};

choice SimpleChoice(TestEnum selector) on selector
{
    case One:
        ; // empty
    case TWO:
        SimpleUnion fieldTwo;
    default:
        string fieldDefault;

    function uint32 fieldTwoFuncCall()
    {
        return fieldTwo.simpleStructFieldU32();
    }
};

struct TemplatedStruct<T>
{
    T field;
};

instantiate TemplatedStruct<uint32> TS32;

struct TemplatedParameterizedStruct<T>(T param)
{
    uint32 array[param.field];
};

subtype SimpleStruct SubtypedSimpleStruct;

struct WithTypeInfoCode
{
    SubtypedSimpleStruct simpleStruct;
    ComplexStruct complexStruct;
    ParameterizedStruct(simpleStruct) parameterizedStruct;
    TestEnum selector;
    SimpleChoice(selector) simpleChoice;
    TemplatedStruct<uint32> templatedStruct;
    TemplatedParameterizedStruct<TemplatedStruct<uint32>>(templatedStruct) templatedParameterizedStruct;
    extern externData;
    implicit uint32 implicitArray[];
};

sql_table SqlTable
{
    uint32 pk sql "PRIMARY KEY NOT NULL";
    string text;
};

sql_table TemplatedSqlTable<T>
{
    T pk sql "NOT NULL";
    WithTypeInfoCode withTypeInfoCode;

    sql "PRIMARY KEY(pk)";
};

sql_table Fts4Table using fts4
{
    sql_virtual int64 docId;
    string searchTags;
};

sql_table WithoutRowidTable
{
    uint32 pk1 sql "NOT NULL";
    uint32 pk2 sql "NOT NULL";

    sql "PRIMARY KEY(pk1, pk2)";
    sql_without_rowid;
};

instantiate TemplatedSqlTable<uint8> TemplatedSqlTableU8;

sql_database SqlDatabase
{
    SqlTable sqlTable;
    TemplatedSqlTable<uint32> templatedSqlTableU32;
    TemplatedSqlTableU8 templatedSqlTableU8;
    Fts4Table fts4Table;
    WithoutRowidTable withoutRowidTable;
};

pubsub SimplePubsub
{
    publish topic("simpleStruct") SimpleStruct pubSimpleStruct;
    subscribe topic("simpleStruct") SimpleStruct subSimpleStruct;
};

service SimpleService
{
    SimpleStruct getSimpleStruct(SimpleUnion);
};
