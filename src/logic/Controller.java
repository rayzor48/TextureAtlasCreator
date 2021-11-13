package logic;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static List<File> toFiles;
    private static ImageEditor editor;
    private static List<BufferedImage> files;
    private static List<BufferedImage> demoFiles;
    private static BufferedImage image;
    private static BufferedImage demoImage;

    public static void createEditor(){
        if(editor == null){
            editor = new ImageEditor();

            //говнокод
            ImageReader ir = new ImageReader();
            ir.demoImageReader(130);
            demoFiles = ir.getImages();

            editor.createDemoAtlas((ArrayList<BufferedImage>) demoFiles, new Point(3, 3));
            demoImage = editor.getImage();
        }
    }

    public static void openFiles(List<File> files){
        setFile(files);
        readFiles();
    }

    private static void setFile(List<File> files){
        toFiles = files;
    }

    private static void readFiles(){
        ImageReader ir = new ImageReader();
        ir.ReadFiles(toFiles);
        files = ir.getImages();
    }

    public static void doProcessImage(Point grid){
        if(files == null){
            System.out.println("files = null");
        }

         editor.createAtlas((ArrayList<BufferedImage>) files, grid);
         image = editor.getImage();
    }

    public static void doDemoProcessImage(Point grid){
        if(files == null){
            System.out.println("files = null");
        }
        editor.createAtlas((ArrayList<BufferedImage>) demoFiles, grid);
        image = editor.getImage();
    }

    public static WritableImage getImage(){
        return convertToFxImage(image);
    }

    public static WritableImage getDemoImage(){
        return convertToFxImage(demoImage);
    }

    private static WritableImage convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return wr;
    }

    public static void saveFile(File file, String format){
       ImageWriter iw = new ImageWriter();
       iw.writeImage(file, image, format);
    }

}
