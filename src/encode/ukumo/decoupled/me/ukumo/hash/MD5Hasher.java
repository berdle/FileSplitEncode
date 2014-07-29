package encode.ukumo.decoupled.me.ukumo.hash;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 11:46 PM
 */
public class MD5Hasher implements HashInterface {

    @Override
    public String hashStringToString(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] sourceBytes = source.getBytes("UTF-8");
            byte[] hashed = md.digest(sourceBytes);
            BigInteger bi = new BigInteger(1, hashed);
            String hashText = bi.toString(16);
            // we can pad with 0's but it doesn't really matter, what matters is that we get the same
            // value each time so we can look up files.

            // what to do about collisions though? there's always collisions :(
            // encrypt instead of hash??
            hashText = padString(hashText);
            return hashText;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //return 0L;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //return 0L;
        }

        return null;
    }

    @Override
    public byte[] hashStringToBytes(String source) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] sourceBytes = source.getBytes("UTF-8");
            byte[] hashed = md.digest(sourceBytes);

            return hashed;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //return 0L;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            //return 0L;
        }

        return null;
    }



    //TODO: Unimplemented!!!
    @Override
    public byte[] hashFile(File input) {
        try {
            FileInputStream fIn = new FileInputStream(input);

            //fIn.

        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public byte[] hashByteArrayToBytes(byte[] source) {
        byte[] hashed = null;
        try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        hashed = md.digest(source);

        } catch (NoSuchAlgorithmException e) {
            //TODO: Handle gracefully
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return hashed;
    }

    @Override
    public String hashByteArrayToString(byte[] source) {
        byte[] hashed = hashByteArrayToBytes(source);
        BigInteger bi = new BigInteger(1, hashed);
        String hashText = bi.toString(16);
        hashText = padString(hashText);
        return hashText;
    }

    private String padString(String hashText)
    {
        if (hashText.length() < 32) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 32 - hashText.length(); i++) {
                sb.append("0");
            }
            sb.append(hashText);
            hashText = sb.toString();
        }
        return hashText;
    }

}
