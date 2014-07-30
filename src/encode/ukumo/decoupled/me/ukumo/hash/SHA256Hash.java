package encode.ukumo.decoupled.me.ukumo.hash;

import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Mr_Appleby
 * Date: 30/07/14
 * Time: 5:05 PM
 */
public class SHA256Hash implements HashInterface {

    private MessageDigest digest;
    private int hexLen;

    public SHA256Hash() {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("sha 256 enabled!");
        hexLen = 64;
    }

    @Override
    public String hashStringToString(String source) {

        try {
            byte[] b = source.getBytes("UTF-8");
            return hashByteArrayToString(b);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public byte[] hashStringToBytes(String source) {
        try {
            byte[] b = source.getBytes("UTF-8");
            return hashByteArrayToBytes(b);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public byte[] hashFile(File input) {
        //TODO: NOT IMPLEMENTED, chunks are hashed while still byte arrays
        return null;
    }

    @Override
    public byte[] hashByteArrayToBytes(byte[] source) {
        return digest.digest(source);
    }

    @Override
    public String hashByteArrayToString(byte[] source) {
        byte[] b = hashByteArrayToBytes(source);
        String out = Hex.toHexString(b);
        BigInteger bi = new BigInteger(1, b);
        System.out.println("Returning hash: " + out);
        return out;
    }

    private String padString(int len, String source) {
        if (source.length() < len) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len - source.length(); i++) {
                sb.append("0");
            }
            sb.append(source);
            source = sb.toString();
        }
        return source;
    }

}
