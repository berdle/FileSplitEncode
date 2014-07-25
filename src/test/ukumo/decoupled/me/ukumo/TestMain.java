package test.ukumo.decoupled.me.ukumo;

import encode.ukumo.decoupled.me.ukumo.compression.LZMADecoder;
import encode.ukumo.decoupled.me.ukumo.compression.LZMAEncoder;
import encode.ukumo.decoupled.me.ukumo.encryption.EncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.encryption.PassthroughEncryptionHandler;
import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;
import encode.ukumo.decoupled.me.ukumo.exceptions.EncodeException;
import encode.ukumo.decoupled.me.ukumo.handlers.DecodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.EncodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.SampleDecodedHandler;
import encode.ukumo.decoupled.me.ukumo.handlers.SampleEncodedHandler;
import encode.ukumo.decoupled.me.ukumo.hash.MD5Hasher;
import encode.ukumo.decoupled.me.ukumo.packer.Joiner;
import encode.ukumo.decoupled.me.ukumo.packer.Splitter;

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
        MD5Hasher md5 = new MD5Hasher();
        String chunkPrefix = md5.hashToString(fname);

        LZMAEncoder encLZ = new LZMAEncoder();
        LZMADecoder decLZ = new LZMADecoder();
        DecodedHandler decHandle = new SampleDecodedHandler(fname + ".rebuilt");
        EncodedHandler encHandle = new SampleEncodedHandler();

        EncryptionHandler crypt = new PassthroughEncryptionHandler();

        try {
            Splitter fsp = new Splitter(1000000);
            fsp.split(fname, chunkPrefix, encLZ, encHandle, crypt);

            Joiner joiner = new Joiner();
            String path = "";
            joiner.rebuild(path, chunkPrefix, decLZ, decHandle, crypt);

        } catch (EncodeException e) {
            e.printStackTrace();
        } catch (DecodeException e) {
            e.printStackTrace();
        }
    }


}
