package zserio.runtime.io;

import java.io.IOException;

import zserio.runtime.ZserioError;

/**
 * Interface for writing to bit stream with offset initialization for classes generated by Zserio.
 */
public interface InitializeOffsetsWriter extends Writer
{
    /**
     * Writes this objects to the given bit stream and optionally calls initializeOffsets.
     *
     * @param out                   The bit stream writer to use.
     * @param callInitializeOffsets True to call initializeOffsets on the object being written.
     *
     * @throws IOException     Throws if the writing failed.
     * @throws ZserioError Throws if some Zserio error occurred.
     */
    public void write(zserio.runtime.io.BitStreamWriter out, boolean callInitializeOffsets)
            throws IOException, ZserioError;

    /**
     * Initializes indexed offsets.
     *
     * @param bitPosition Current bit stream position.
     *
     * @return Updated bit stream position which points to the first bit after the array.
     *
     * @throws ZserioError Throws if some Zserio error occurred.
     */
    public long initializeOffsets(long bitPosition) throws ZserioError;
}
