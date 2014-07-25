package encode.ukumo.decoupled.me.ukumo.handlers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Mr_Appleby
 * Date: 24/07/14
 * Time: 11:33 PM
 */
public class SampleDecodedHandler implements DecodedHandler {

    private String reconName;
    private FileOutputStream out;


    public SampleDecodedHandler(String reconstructionName) {
        reconName = reconstructionName;
        try {
            out = new FileOutputStream(reconName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDecode(byte[] data) {
        try {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finished() {
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
