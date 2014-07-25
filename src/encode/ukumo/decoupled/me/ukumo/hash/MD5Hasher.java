package encode.ukumo.decoupled.me.ukumo.hash;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 11:46 PM
 */
public class MD5Hasher implements StringHashIface {

    @Override
    public String hashToString(String source) {
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
}
