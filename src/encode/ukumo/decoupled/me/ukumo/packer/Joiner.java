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

    public byte[] expandChunk(DataInputStream dis, LZMADecoder dec, EncryptionHandler crypt) throws IOException, DecodeException {
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
