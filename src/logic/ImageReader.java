package logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageReader {

    private List<BufferedImage> images;
    private List<BufferedImage> demoImages;

    public ImageReader() {
        images = new ArrayList<BufferedImage>();
    }

    public ImageReader(List<File> files) {
        images = new ArrayList<BufferedImage>();
        ReadFiles(files);
    }

    public void demoImageReader(int max)  {
        String path = "Images/kk/";
        List<File> files= new ArrayList<File>();
        for(int i = 1; i <= 130 && i < max; i++){
            path = "D:\\Labs_JAVA\\Ulitka\\TextureAtlasCreator\\src\\Images\\" + i + ".png";
            files.add(new File( path));
        }

        ReadFiles(files);
    }

    public void ReadFiles(List<File> files) {

        System.out.println("first element path = " + files.get(0));
         try {
             for(File f : files){
                 images.add(ImageIO.read(f));
             }
         } catch (Exception e) {
             e.printStackTrace();
             System.err.println("Invalid file exception");
         }
     }

     public List<BufferedImage> getImages(){
        return images;
     }

     public List<BufferedImage> getDemoImages(){
        return demoImages;
     }
}
