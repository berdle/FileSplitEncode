package encode.ukumo.decoupled.me.ukumo.compression;

import SevenZip.Compression.LZMA.Encoder;
import SevenZip.ICodeProgress;
import encode.ukumo.decoupled.me.ukumo.exceptions.CompressionException;

import java.io.*;

/**
 * User: Mr_Appleby
 * Date: 16/07/14
 * Time: 11:08 AM
 */
public class LZMAEncoder {

    private Encoder encoder;

    private int algorithm;      // algorithm 1 or 2, default 2?
    private int dictionarySize; // dictionary size, default 1 << 23(8MB)
    private int fastBytes;      // fast bytes, 5 - 273, default 128
    private int contextBits;    // literal context bits, 0 - 8, default 3
    private int lPosBits;       // literal pos bits, 0 - 4. default 0
    private int posBits;        // 0 - 4, default 0
    private int matchFinder;    // bt2 or bt4
    private boolean endOfStreamMarker; //write eos marker?


    public LZMAEncoder(int _dictionarySize) {
        encoder = new SevenZip.Compression.LZMA.Encoder();
        algorithm = 2;
        dictionarySize = _dictionarySize;
        fastBytes = 128;
        contextBits = 3;
        lPosBits = 0;
        posBits = 0;
        matchFinder = 1;
        endOfStreamMarker = false;
    }

    public byte[] compress(byte[] source) throws CompressionException
    {
        encoder.SetAlgorithm(algorithm);
        encoder.SetDictionarySize(dictionarySize);
        encoder.SetLcLpPb(contextBits, lPosBits, posBits);
        encoder.SetMatchFinder(matchFinder);
        encoder.SetNumFastBytes(fastBytes);
        encoder.SetEndMarkerMode(endOfStreamMarker);

        byte[] out = new byte[source.length]; // len?
        InputStream is = new ByteArrayInputStream(source);
        OutputStream os = new ByteArrayOutputStream();

        long inSize = source.length;
        long outSize = out.length;


        ICodeProgress icp = new ICodeProgress() {
            @Override
            public void SetProgress(long inSize, long outSize) {
                //System.out.println("progressing " + inSize + " of " + outSize);
            }
        };

        byte[] data = null;
        try {

            encoder.WriteCoderProperties(os);
            long fileSize = source.length;

            for (int i = 0; i < 8; i++)
                os.write((int)(fileSize >>> (8 * i)) & 0xFF);

            encoder.Code(is, os, -1, -1, icp);
            is.close();
            data = ((ByteArrayOutputStream)os).toByteArray();
            os.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new CompressionException("Compression failed: IO Exception");
        }

        return data;
    }

}
