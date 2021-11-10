package logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageReader {

    private List<BufferedImage> images;

    public ImageReader() {
        images = new ArrayList<BufferedImage>();
    }

    public ImageReader(List<File> files) {
        images = new ArrayList<BufferedImage>();
        ReadFiles(files);
    }

    public void ReadFiles(List<File> files){
         try {
             for(File f : files){
                 images.add(ImageIO.read(f));
             }
         } catch (Exception e) {
             System.err.println("Invalid file exception");
         }
     }

     public List<BufferedImage> getImages(){
        return images;
     }
}
