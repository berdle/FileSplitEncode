package encode.ukumo.decoupled.me.ukumo.packer;

import encode.ukumo.decoupled.me.ukumo.compression.LZMADecoder;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecompressionException;
import encode.ukumo.decoupled.me.ukumo.handlers.DecodedHandler;

import java.io.*;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 3:52 PM
 */
public class Joiner {

    public Joiner(){}

    public void rebuild(String path, String chunkPrefix, LZMADecoder dec, DecodedHandler handler, EncryptionHandler decrypt) throws DecodeException {
        // look for chunks starting with hash-chunkId.uko

        if (handler == null || decrypt == null || dec == null) {
            throw new DecodeException();
        }

        StringBuilder sb = new StringBuilder();
        chunkPrefix = path + chunkPrefix;
        sb.append(chunkPrefix);
        sb.append("-0.uko");
        int chunkId = 0;

        File inFile = new File(sb.toString());

        try {

            DataInputStream dis;
            File next = inFile;

            do {
                dis = new DataInputStream(new FileInputStream(next));
                handler.onDecode(expandChunk(dis, dec, decrypt));
                chunkId++;

                StringBuilder ns = new StringBuilder();
                ns.append(chunkPrefix);
                ns.append("-");
                ns.append(chunkId);
                ns.append(".uko");
                dis.close();

                next = new File(ns.toString());
            } while (next.exists());

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
