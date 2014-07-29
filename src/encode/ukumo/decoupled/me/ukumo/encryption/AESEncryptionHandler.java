package encode.ukumo.decoupled.me.ukumo.encryption;

import encode.ukumo.decoupled.me.ukumo.hash.HashInterface;
import encode.ukumo.decoupled.me.ukumo.hash.MD5Hasher;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * User: Mr_Appleby
 * Date: 29/07/14
 * Time: 10:24 AM
 */
public class AESEncryptionHandler implements EncryptionHandler {

    private BlockCipher engine;
    private KeyParameter kParam;

    public AESEncryptionHandler() {
        engine = new AESEngine();

        String key = new String ("A sample key!");
        HashInterface shi = new MD5Hasher();

        byte[] kp = shi.hashStringToBytes(key);

        kParam = new KeyParameter(kp);

    }


    @Override
    public byte[] encrypt(byte[] data) {
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);
        cipher.init(true, kParam);
        byte[] ret = new byte[cipher.getOutputSize(data.length)];

        int lenCheck = cipher.processBytes(data, 0, data.length, ret, 0);
        try {
            cipher.doFinal(ret, lenCheck);
        } catch (InvalidCipherTextException e) {
            // TODO: error handle the exception
            // throw unable to cipher or some such
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return ret;
    }

    @Override
    public byte[] decrypt(byte[] data) {
        //TODO:: fix code duplication! really same method
        //but with true or false in the cipher.init call (true = encrypt)
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(engine);

        cipher.init(false, kParam);
        byte[] ret = new byte[cipher.getOutputSize(data.length)];

        int lenCheck = cipher.processBytes(data, 0, data.length, ret, 0);
        try {
            cipher.doFinal(ret, lenCheck);
        } catch (InvalidCipherTextException e) {
            // TODO: error handle the exception
            // throw unable to cipher or some such
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return ret;
    }
}
