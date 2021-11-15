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

    public void getListPuthPatternImages(int countDemoImages)  {
        String path;
        String absolutePath = new File("./").getAbsolutePath();
        absolutePath = absolutePath.substring(0, absolutePath.length() - 1);

        List<File> files= new ArrayList<File>();

        for(int i = 1; i <= 130 && i < countDemoImages; i++){
            path = absolutePath + "src\\Images\\" + i + ".png";//D:\\Labs_JAVA\\Ulitka\\TextureAtlasCreator\\src\\Images\\" + i + ".png";
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
}
