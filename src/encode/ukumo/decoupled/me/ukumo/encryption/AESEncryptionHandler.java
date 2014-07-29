package encode.ukumo.decoupled.me.ukumo.encryption;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * User: Mr_Appleby
 * Date: 29/07/14
 * Time: 10:24 AM
 */
public class AESEncryptionHandler implements EncryptionHandler {

    private BlockCipher cipher;
    private KeyParameter kParam;

    public AESEncryptionHandler() {
        cipher = new AESEngine();
        String key = new String ("A sample key!");
        byte[] kp = key.getBytes();
        kParam = new KeyParameter(kp);
    }


    @Override
    public byte[] encrypt(byte[] data) {
        byte[] ret = new byte[data.length];

        return null;
    }

    @Override
    public byte[] decrypt(byte[] data) {

        return null;
    }
}
