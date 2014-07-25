package encode.ukumo.decoupled.me.ukumo.handlers;

import encode.ukumo.decoupled.me.ukumo.exceptions.DecodeException;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 10:08 PM
 */
public interface DecodedHandler {

    /***
     * anything you'd like to do per chunk as it is decoded.
     * @param data
     */
    public void onDecode(byte[] data) throws DecodeException;
    public void finished() throws DecodeException;

}
