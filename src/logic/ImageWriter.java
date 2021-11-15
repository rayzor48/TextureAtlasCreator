package logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public void writeImage(String pathNameTo, BufferedImage image, String format){
        File output = new File(pathNameTo);
        writeImage(output, image, format);
    }

    public void writeImage(File fileTo, BufferedImage image, String format) {

        try {
            System.out.println("res = " + ImageIO.write(image, format, fileTo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
