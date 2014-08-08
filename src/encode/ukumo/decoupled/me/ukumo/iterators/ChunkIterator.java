package encode.ukumo.decoupled.me.ukumo.iterators;

import encode.ukumo.decoupled.me.ukumo.packer.ChunkParams;

import java.io.DataInputStream;

/**
 * User: Mr_Appleby
 * Date: 25/07/14
 * Time: 8:35 PM
 */
public interface ChunkIterator {

    /**
     * Returns the next chunk in the sequence as a DataInputStream
     * @param params nextChunk should fill out the params object with information about the returned chunk
     * @return
     */
    public DataInputStream nextChunk(ChunkParams params);

}
