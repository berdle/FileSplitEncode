package encode.ukumo.decoupled.me.ukumo.encryption;

/**
 * User: Mr_Appleby
 * Date: 25/07/14
 * Time: 2:57 PM
 */
public interface EncryptionHandler {

    public byte[] encrypt(byte[] data);
    public byte[] decrypt(byte[] data);

}
