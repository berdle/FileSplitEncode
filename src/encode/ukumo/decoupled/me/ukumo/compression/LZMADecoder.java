package encode.ukumo.decoupled.me.ukumo.compression;

import SevenZip.Compression.LZMA.Decoder;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecompressionException;

import java.io.*;

/**
 * User: Mr_Appleby
 * Date: 16/07/14
 * Time: 4:11 PM
 */
public class LZMADecoder {

    private final static int NUM_PROPERTIES = 5;
    private Decoder decoder;

    public LZMADecoder()
    {
        decoder = new Decoder();
    }

    public byte[] decode(byte[] source) throws DecompressionException {

        byte[] data = null;
        byte[] properties = new byte[NUM_PROPERTIES];

        InputStream is = new ByteArrayInputStream(source);
        OutputStream os = new ByteArrayOutputStream();

        try {
            if (is.read(properties, 0, NUM_PROPERTIES) != NUM_PROPERTIES) {
                throw new DecompressionException("Zip file too short!");
            }

            if (!decoder.SetDecoderProperties(properties)) {
                throw new DecompressionException("Unable to set decoder properties");
            }

            long outsize = 0L;
            for (int i = 0; i < 8; i++)
            {
                int v = is.read();

                if (v < 0) {
                    throw new DecompressionException("Can't read stream size");
                }
                outsize |= ((long)v) << (8 * i);
            }

            if (!decoder.Code(is, os, outsize)) {
                throw new DecompressionException("Error in data stream");
            }

            data = ((ByteArrayOutputStream)os).toByteArray();

        } catch (IOException e1) {
            e1.printStackTrace();
            throw new DecompressionException("Decompress failed: IO Exception");
        }

        return data;
    }

}
