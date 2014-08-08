package encode.ukumo.decoupled.me.ukumo.packer;

import encode.ukumo.decoupled.me.ukumo.compression.LZMADecoder;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecompressionException;
import encode.ukumo.decoupled.me.ukumo.handlers.DecodedHandler;
import encode.ukumo.decoupled.me.ukumo.iterators.ChunkIterator;

import java.io.*;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 3:52 PM
 */
public class Joiner {

    public Joiner(){}

    /**
     * rebuild decodes chunks selected from the iterator and calls the decode handler on each chunk.
     *
     * @param iterator A ChunkIterator object that supplies data to the method.
     * @param dec A LZMA decoder
     * @param handler Object implementing the DecodedHandler interface, to handle decoded chunks
     * @param crypt Object implementing the EncryptionHandler interface
     * @throws DecodeException
     */
    public void rebuild(ChunkIterator iterator, LZMADecoder dec, DecodedHandler handler, EncryptionHandler crypt) throws DecodeException {
        // look for chunks starting with hash-chunkId.uko

        if (handler == null || crypt == null || dec == null) {
            throw new DecodeException();
        }

        try {

            DataInputStream dis;
            ChunkParams params = new ChunkParams();
            do {
                dis = iterator.nextChunk(params);

                if (params.isValidChunk()) {
                    handler.onDecode(expandChunk(dis, dec, crypt));
                }

            } while (params.getChunkId() < params.getTotalChunks() - 1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new DecodeException("Decode failed: file not found");
        } catch (IOException e) {
            e.printStackTrace();
            throw new DecodeException("Decode failed: IO Error");
        }
    }

    /**
     *
     * @param dis Chunk as a data input stream
     * @param dec Reference to the decoder
     * @param crypt Reference to the decrypter
     * @return decrypted & decompressed chunk as byte[]
     * @throws IOException
     * @throws DecodeException
     */
    private byte[] expandChunk(DataInputStream dis, LZMADecoder dec, EncryptionHandler crypt) throws IOException, DecodeException {
        int buff = 1024;
        byte[] b;
        int r;
        ByteArrayOutputStream bao = new ByteArrayOutputStream();

        do {
            b = new byte[buff];
            r = dis.read(b);

            if (r > 0) {
                bao.write(b, 0, r);
            }
        } while (r >= buff);

        try {
            return dec.decode(crypt.decrypt(bao.toByteArray()));
        } catch (DecompressionException e) {
            e.printStackTrace();
            throw new DecodeException("Decode failed: decompression exception");
        }
    }

}
