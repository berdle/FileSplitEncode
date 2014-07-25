package encode.ukumo.decoupled.me.ukumo.encryption;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 9:06 PM
 */
public class PassthroughEncryptionHandler implements EncryptionHandler {

    public PassthroughEncryptionHandler() {
        //type, hash, stuff...
    }

    public byte[] encrypt(byte[] data) {
        return data;
    }

    public byte[] decrypt(byte[] data) {
        return data;
    }

}
