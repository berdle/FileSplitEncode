package encode.ukumo.decoupled.me.ukumo.handlers;

import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 10:08 PM
 */
public interface DecodedHandler {

    /**
     * anything you'd like to do per chunk as it is decoded.
     * eg. write all chunks sequentially to a file.
     * @param data the decoded chunk as a byte array.
     * @throws DecodeException
     * @return nothing
     */
    public void onDecode(byte[] data) throws DecodeException;

    /**
     * finished() should be called when all the chunks have been decoded.
     * The user should close any files they are writing to in here.
     * @throws DecodeException
     */
    public void finished() throws DecodeException;

}
