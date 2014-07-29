package encode.ukumo.decoupled.me.ukumo.hash;

import java.io.File;

/**
 * User: Mr_Appleby
 * Date: 15/07/14
 * Time: 11:44 PM
 */
public interface HashInterface {
    public String hashStringToString(String source);
    public byte[] hashStringToBytes(String source);
    public byte[] hashFile(File input);
    public byte[] hashByteArrayToBytes(byte[] source);
    public String hashByteArrayToString(byte[] source);
}
