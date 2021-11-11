package logic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private static char [][] mapPicture;
    private static char [][] mapPictureFilter;
    private static String pathToFile;//"C:\\Users\\rayzo\\Desktop\\image1.png"
    private static List<File> toFiles;
    private static ImageEditor editor;
    private static List<BufferedImage> files;
    private static BufferedImage image;


    public static void createEditor(){
        if(editor == null){
            editor = new ImageEditor();
        }
    }

    public static void setFile(List<File> file){
        toFiles = file;
    }

    public static void doProcessImage(Point grid){
        if(files == null){
            System.out.println("files = null");
        }
         editor.createAtlas((ArrayList<BufferedImage>) files, grid);
         image = editor.getImage();
    }

    public static void openFiles(){
        ImageReader ir = new ImageReader();
        ir.ReadFiles(toFiles);
        files = ir.getImages();
    }

    public static void saveFile(File file, String format){
       ImageWriter iw = new ImageWriter();
       iw.writeImage(file, image, format);
    }

}
