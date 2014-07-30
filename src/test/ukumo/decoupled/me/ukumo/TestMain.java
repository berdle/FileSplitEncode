package test.ukumo.decoupled.me.ukumo;

import encode.ukumo.decoupled.me.ukumo.compression.LZMADecoder;
import encode.ukumo.decoupled.me.ukumo.compression.LZMAEncoder;
import encode.ukumo.decoupled.me.ukumo.encryption.AESEncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;
import encode.ukumo.decoupled.me.ukumo.exceptions.EncodeException;
import encode.ukumo.decoupled.me.ukumo.handlers.DecodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.EncodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.SampleDecodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.SampleEncodedHandler;
import encode.ukumo.decoupled.me.ukumo.hash.HashInterface;
import encode.ukumo.decoupled.me.ukumo.hash.SHA256Hash;
import encode.ukumo.decoupled.me.ukumo.packer.*;
import encode.ukumo.decoupled.me.ukumo.iterators.ChunkIterator;
import encode.ukumo.decoupled.me.ukumo.iterators.FileChunkIterator;

import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 2:27 PM
 *
 * Sample usage of the file splitter/packer/stuffer/slicer/gluer/etcer.
 *
 */
public class TestMain {

    public static void main(String[] args){

        /* ask for an input file name */

        System.out.println("Input filename: ");
        Scanner sc = new Scanner(System.in);
        String fname = sc.nextLine();
        /* generate hashed stuff! */
        //MD5Hasher md5 = new MD5Hasher();
        HashInterface hash = new SHA256Hash();

        String chunkPrefix = hash.hashStringToString(fname);

        int dictionarySize = 1 << 21;
        LZMAEncoder encLZ = new LZMAEncoder(dictionarySize);
        LZMADecoder decLZ = new LZMADecoder();
        DecodedHandler decHandle = new SampleDecodedHandler(fname + ".rebuilt");
        EncodedHandler encHandle = new SampleEncodedHandler();

        //EncryptionHandler crypt = new PassthroughEncryptionHandler();

        EncryptionHandler crypt = new AESEncryptionHandler("a sample key");


        String path = "";


        try {
            Splitter fsp = new Splitter(1000000);
            fsp.split(fname, chunkPrefix, encLZ, encHandle, crypt);

            Joiner joiner = new Joiner();

            ChunkIterator defaultFileGrabber = new FileChunkIterator(path, chunkPrefix);
            joiner.rebuild(defaultFileGrabber, decLZ, decHandle, crypt);

        } catch (EncodeException e) {
            e.printStackTrace();
        } catch (DecodeException e) {
            e.printStackTrace();
        }
    }


}
