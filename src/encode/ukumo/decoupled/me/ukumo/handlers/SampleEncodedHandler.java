package encode.ukumo.decoupled.me.ukumo.handlers;

import encode.ukumo.decoupled.me.ukumo.exceptions.EncodeException;
import encode.ukumo.decoupled.me.ukumo.packer.FileData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 10:56 PM
 */
public class SampleEncodedHandler implements EncodedHandler {

    public void onEncode(FileData info, long chunkId, byte[] data) throws EncodeException {
        // could upload the file here if preferred to writing locally.

        StringBuilder sb = new StringBuilder();
        sb.append(info.getChunkPrefix());
        sb.append("-");
        sb.append(chunkId);
        sb.append(".uko");
        String filename = sb.toString();

        try {
            FileOutputStream nFile = new FileOutputStream(filename);
            nFile.write(data, 0, data.length);
            nFile.flush();

            nFile.close();
        } catch (FileNotFoundException e) {
            throw new EncodeException("Error, handler unable to create output file.");
        } catch (IOException e) {
            throw new EncodeException("Error, handler unable to write to output file.");
        }
    }

    public void finished(long totalBytes, long totalChunks) {
        // do clean up, upload files, etc.
    }

}
