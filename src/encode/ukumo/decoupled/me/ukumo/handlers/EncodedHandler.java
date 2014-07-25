package encode.ukumo.decoupled.me.ukumo.handlers;

import encode.ukumo.decoupled.me.ukumo.exceptions.EncodeException;
import encode.ukumo.decoupled.me.ukumo.packer.FileData;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 10:08 PM
 */

public interface EncodedHandler {

    /***
     * Handler called when a file chunk has been zipped and encrypted.
     * Use this to eg. upload the chunk to a particular place.
     * @param data encoded chunk of data
     */
    public void onEncode(FileData info, long chunkId, byte[] data) throws EncodeException;
    public void finished(long totalBytes, long totalChunks) throws EncodeException;
}