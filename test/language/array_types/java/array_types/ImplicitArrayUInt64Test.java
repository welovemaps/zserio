package array_types;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.File;
import java.math.BigInteger;

import org.junit.Test;

import array_types.implicit_array_uint64.ImplicitArray;

import zserio.runtime.ZserioError;
import zserio.runtime.array.BigIntegerArray;
import zserio.runtime.io.BitStreamReader;
import zserio.runtime.io.BitStreamWriter;
import zserio.runtime.io.FileBitStreamReader;
import zserio.runtime.io.FileBitStreamWriter;

public class ImplicitArrayUInt64Test
{
    @Test
    public void bitSizeOf() throws IOException, ZserioError
    {
        final int numElements = 44;
        BigIntegerArray array = new BigIntegerArray(numElements);
        for (int i = 0; i < numElements; ++i)
            array.setElementAt(BigInteger.valueOf(i), i);

        final ImplicitArray implicitArray = new ImplicitArray(array);
        final int bitPosition = 2;
        final int implicitArrayBitSize = numElements * 64;
        assertEquals(implicitArrayBitSize, implicitArray.bitSizeOf(bitPosition));
    }

    @Test
    public void initializeOffsets() throws IOException, ZserioError
    {
        final int numElements = 66;
        BigIntegerArray array = new BigIntegerArray(numElements);
        for (int i = 0; i < numElements; ++i)
            array.setElementAt(BigInteger.valueOf(i), i);

        final ImplicitArray implicitArray = new ImplicitArray(array);
        final int bitPosition = 2;
        final int expectedEndBitPosition = bitPosition + numElements * 64;
        assertEquals(expectedEndBitPosition, implicitArray.initializeOffsets(bitPosition));
    }

    @Test
    public void read() throws IOException, ZserioError
    {
        final File file = new File("test.bin");
        final int numElements = 99;
        writeImplicitArrayToFile(file, numElements);
        final BitStreamReader stream = new FileBitStreamReader(file);
        final ImplicitArray implicitArray = new ImplicitArray(stream);
        stream.close();

        final BigIntegerArray array = implicitArray.getArray();
        assertEquals(numElements, array.length());
        for (int i = 0; i < numElements; ++i)
            assertEquals(BigInteger.valueOf(i), array.elementAt(i));
    }

    @Test
    public void write() throws IOException, ZserioError
    {
        final int numElements = 55;
        BigIntegerArray array = new BigIntegerArray(numElements);
        for (int i = 0; i < numElements; ++i)
            array.setElementAt(BigInteger.valueOf(i), i);

        ImplicitArray implicitArray = new ImplicitArray(array);
        final File file = new File("test.bin");
        final BitStreamWriter writer = new FileBitStreamWriter(file);
        implicitArray.write(writer);
        writer.close();

        final ImplicitArray readImplicitArray = new ImplicitArray(file);
        final BigIntegerArray readArray = readImplicitArray.getArray();
        assertEquals(numElements, readArray.length());
        for (int i = 0; i < numElements; ++i)
            assertEquals(BigInteger.valueOf(i), readArray.elementAt(i));
    }

    private void writeImplicitArrayToFile(File file, int numElements) throws IOException
    {
        final FileBitStreamWriter writer = new FileBitStreamWriter(file);

        for (int i = 0; i < numElements; ++i)
            writer.writeBigInteger(BigInteger.valueOf(i), 64);

        writer.close();
    }
}
