package encode.ukumo.decoupled.me.ukumo.packer;

import java.io.DataInputStream;

/**
 * User: Mr_Appleby
 * Date: 25/07/14
 * Time: 8:53 PM
 */
public class ChunkParams {

    /**
     * totalChunks - total chunks, 1..n
     * chunkId - current chunk,    0..n-1
     * validChunk - boolean, should indicate the stream is usable and correct file.
     */

    private long chunkId;
    private long totalChunks;
    private boolean validChunk;

    public ChunkParams() {
        chunkId = 0;
        totalChunks = 0;
        validChunk = false;
    }

    public void setValidChunk(boolean v) {
        validChunk = v;
    }

    public void setChunkId(long id) {
        chunkId = id;
    }

    public void setTotalChunks(long total) {
        totalChunks = total;
    }

    public long getChunkId() {
        return chunkId;
    }

    public long getTotalChunks() {
        return totalChunks;
    }

    /**
     * Was the chunk iterator able to pass back a valid chunk?
     * @return true/false
     */
    public boolean isValidChunk() {
        return validChunk;
    }

}
