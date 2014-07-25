package encode.ukumo.decoupled.me.ukumo.packer;

import java.io.File;
import java.util.Date;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 10:25 PM
 */
public class FileData {
    private String originalName;
    private String chunkPrefix;
    private Date fileModifiedDate;
    private Long fileModifiedMillis;

    public FileData(File file, String _chunkPrefix) {
        originalName = file.getName();
        chunkPrefix = _chunkPrefix;
        fileModifiedMillis = file.lastModified();
        fileModifiedDate = new Date(fileModifiedMillis);
    }

    public String getChunkPrefix() {
        return chunkPrefix;
    }

    public String getOriginalFileName() {
        return originalName;
    }

    public Date getFileModifiedDate() {
        return fileModifiedDate;
    }

    public long getFileModifiedMillis() {
        return fileModifiedMillis;
    }
}
