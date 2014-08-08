package encode.ukumo.decoupled.me.ukumo.packer;

import encode.ukumo.decoupled.me.ukumo.compression.LZMAEncoder;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.CompressionException;
import encode.ukumo.decoupled.me.ukumo.exceptions.EncodeException;
import encode.ukumo.decoupled.me.ukumo.handlers.EncodedHandler;

import java.io.*;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 2:28 PM
 */
public class Splitter {

    private long bytesPerChunk = 1000000;
    private int readBuffer = 1024;

    /**
     * Creates a default splitter with a chunk size of 1MB
     */
    public Splitter()
    {

    }

    /**
     * Creates a splitter with the specified chunk size
     * @param chunkSize Long representing byte size of chunks (before compression/encryption)
     */
    public Splitter(long chunkSize)
    {
        // max size of chunk before compression
        bytesPerChunk = chunkSize;
    }

    /**
     * Creates a splitter with specified max chunk size, and read buffer size
     * @param chunkSize chunk size in bytes
     * @param bufferSize read buffer size in bytes
     */
    public Splitter(long chunkSize, int bufferSize) {
        bytesPerChunk = chunkSize;
        readBuffer = bufferSize;
    }

    /**
     * Splits a file into chunks which are then compressed and encrypted before being passed to a handler to be
     * written to disc or uploaded, for example.
     * @param fileIn Filepath of the file to split
     * @param chunkPrefix Prefix to append to all chunk names (eg. hash of filename)
     * @param encoder Compression Encoder implemented LZMAEncoder interface
     * @param handler Handler implementing EncodedHandler, deal with finished chunks here.
     * @param encrypt Object implementing EncryptionHandler interface
     * @throws EncodeException
     */
    public void split(String fileIn, String chunkPrefix, LZMAEncoder encoder, EncodedHandler handler, EncryptionHandler encrypt) throws EncodeException {

        if (encoder == null || handler == null || encrypt == null) {
            throw new EncodeException();
        }

        try {

            int buff = readBuffer;
            byte[] b;
            int r;
            int written = 0;
            long chunkId = 0;
            boolean closed = false;

            File fIn = new File(fileIn);
            FileData info = new FileData(fIn, chunkPrefix);
            ByteArrayOutputStream fBuff = new ByteArrayOutputStream();
            DataInputStream input = new DataInputStream(new FileInputStream(fIn));

            // need to write to a byte[] before dumping to disk.
            // unless encode in place?
            // remember limited memory so can't load whole file into ram!

            do {

                b = new byte[buff];
                r = input.read(b, 0 , buff);
                if (r > 0) {
                    fBuff.write(b, 0, r);
                    written += r;
                    if (written >= bytesPerChunk) {
                        written = 0;
                        byte[] enc = encoder.compress(fBuff.toByteArray());

                        handler.onEncode(info, chunkId, encrypt.encrypt(enc.clone()));
                        fBuff.reset();
                        closed = true;
                    }
                }

                if (written == 0) {
                    closed = false;
                    chunkId++;
                }

            } while (r >= buff);

            if (!closed) {
                byte[] enc = encoder.compress(fBuff.toByteArray());
                handler.onEncode(info, chunkId, encrypt.encrypt(enc.clone()));
                fBuff.reset();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new EncodeException("Encode failed: File not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new EncodeException("Encode failed: IO Exception");
        } catch (CompressionException e) {
            throw new EncodeException("Encode failed: Compression exception");
        }

    }

}
