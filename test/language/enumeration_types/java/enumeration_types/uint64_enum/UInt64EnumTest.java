package enumeration_types.uint64_enum;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

import zserio.runtime.io.BitStreamReader;
import zserio.runtime.io.ByteArrayBitStreamReader;
import zserio.runtime.io.ByteArrayBitStreamWriter;

public class UInt64EnumTest
{
    @Test
    public void constructor()
    {
        final DarkColor darkColor = DarkColor.DARK_RED;
        assertEquals(DarkColor.DARK_RED, darkColor);
    }

    @Test
    public void getValue()
    {
        final DarkColor darkColor = DarkColor.dark_blue;
        assertEquals(DARK_BLUE_VALUE, darkColor.getValue());
    }

    @Test
    public void getGenericValue()
    {
        final DarkColor darkColor = DarkColor.DarkGreen;
        assertEquals(DARK_GREEN_VALUE, darkColor.getGenericValue());
    }

    @Test
    public void bitSizeOf()
    {
        final DarkColor darkColor = DarkColor.DarkGreen;
        assertEquals(UINT64_ENUM_BITSIZEOF, darkColor.bitSizeOf());
    }

    @Test
    public void write() throws IOException
    {
        final DarkColor darkColor = DarkColor.DarkGreen;
        final ByteArrayBitStreamWriter writer = new ByteArrayBitStreamWriter();
        darkColor.write(writer);
        final BitStreamReader reader = new ByteArrayBitStreamReader(writer.toByteArray());
        final BigInteger readColor = reader.readBigInteger(UINT64_ENUM_BITSIZEOF);
        assertEquals(readColor, darkColor.getValue());
    }

    @Test
    public void toEnum()
    {
        DarkColor darkColor = DarkColor.toEnum(NONE_COLOR_VALUE);
        assertEquals(DarkColor.noneColor, darkColor);

        darkColor = DarkColor.toEnum(DARK_RED_VALUE);
        assertEquals(DarkColor.DARK_RED, darkColor);

        darkColor = DarkColor.toEnum(DARK_BLUE_VALUE);
        assertEquals(DarkColor.dark_blue, darkColor);

        darkColor = DarkColor.toEnum(DARK_GREEN_VALUE);
        assertEquals(DarkColor.DarkGreen, darkColor);
    }

    @Test(expected=IllegalArgumentException.class)
    public void toEnumFailure()
    {
        DarkColor.toEnum(BigInteger.valueOf(3));
    }

    private static int UINT64_ENUM_BITSIZEOF = 64;

    private static BigInteger NONE_COLOR_VALUE = BigInteger.valueOf(0);
    private static BigInteger DARK_RED_VALUE = BigInteger.valueOf(1);
    private static BigInteger DARK_BLUE_VALUE = BigInteger.valueOf(2);
    private static BigInteger DARK_GREEN_VALUE = BigInteger.valueOf(7);
}
