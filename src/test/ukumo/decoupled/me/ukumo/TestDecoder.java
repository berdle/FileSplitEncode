package test.ukumo.decoupled.me.ukumo;

import encode.ukumo.decoupled.me.ukumo.compression.LZMADecoder;
import encode.ukumo.decoupled.me.ukumo.encryption.AESEncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.encryption.PassthroughEncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;
import encode.ukumo.decoupled.me.ukumo.handlers.DecodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.SampleDecodedHandler;
import encode.ukumo.decoupled.me.ukumo.iterators.ChunkIterator;
import encode.ukumo.decoupled.me.ukumo.iterators.FileChunkIterator;
import encode.ukumo.decoupled.me.ukumo.packer.Joiner;

import javax.crypto.EncryptedPrivateKeyInfo;

/**
 * User: Mr_Appleby
 * Date: 8/08/14
 * Time: 11:54 AM
 */
public class TestDecoder {

    public static void main(String[] args) {

        String fname;
        String key = "a sample key";

        // hardcoded filename. this is just a rough testbed for examining files which may have problems.
        // but may be treated as usage example of decoding.
        String prefix = "de90e9a5d7e8ed900cfdfe81c4da7d3cad3557b821e0c06bb0ca8239783b7af1";

        //EncryptionHandler eh = new AESEncryptionHandler(key);
        EncryptionHandler eh = new PassthroughEncryptionHandler();
        ChunkIterator chit = new FileChunkIterator("", prefix);
        DecodedHandler dech = new SampleDecodedHandler("decode_output");
        LZMADecoder lzdec = new LZMADecoder();
        Joiner j = new Joiner();
        try {
            j.rebuild(chit, lzdec, dech, eh);
        } catch (DecodeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}
