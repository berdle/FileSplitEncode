package encode.ukumo.decoupled.me.ukumo.iterators;

import encode.ukumo.decoupled.me.ukumo.iterators.ChunkIterator;
import encode.ukumo.decoupled.me.ukumo.packer.ChunkParams;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * User: Mr_Appleby
 * Date: 25/07/14
 * Time: 8:37 PM
 */
public class FileChunkIterator implements ChunkIterator {

    private String path;
    private String chunkPrefix;
    private int chunkId;
    private long totalChunks;

    public FileChunkIterator(String _path, String _chunkPrefix) {
        path = _path;
        chunkPrefix = _chunkPrefix;
        chunkId = 0;

        // determine total chunks!
        int i = 0;
        File f;

        do {
            StringBuilder sb = new StringBuilder();
            sb.append(path);
            sb.append(chunkPrefix);
            sb.append("-");
            sb.append(i++);
            sb.append(".uko");

            f = new File(sb.toString());
        } while (f.exists());
        totalChunks = i - 1;

    }

    @Override
    public DataInputStream nextChunk(ChunkParams params) {
        params.setValidChunk(false);
        params.setChunkId(chunkId);
        params.setTotalChunks(totalChunks);


        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append(chunkPrefix);
        sb.append("-");
        sb.append(chunkId);
        sb.append(".uko");

        try {
            File f = new File(sb.toString());
            if (f.exists()) {
                FileInputStream fis = new FileInputStream(sb.toString());
                DataInputStream dis = new DataInputStream(fis);
                params.setValidChunk(true);
                chunkId++;
                return dis;
            } else {
                params.setValidChunk(false);
                return null;
            }

        } catch (FileNotFoundException e) {
            params.setValidChunk(false);
            return null;
        }
    }

}
