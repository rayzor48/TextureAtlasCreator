package logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageEditor {
    private BufferedImage result;


    public void createDemoAtlas(ArrayList<BufferedImage> images, Point targetSize){
        createAtlas(images, targetSize);
    }

    void createAtlas(ArrayList<BufferedImage> images, Point targetSize) {
        try {
            Point nodeResolution = new Point(images.get(0).getWidth(), images.get(0).getHeight());

            Point resultResolution = getTargetResolution(targetSize, nodeResolution);

            result = new BufferedImage(resultResolution.x, resultResolution.y, BufferedImage.TYPE_INT_ARGB);

            int maxValuex = targetSize.x > targetSize.y ? targetSize.x : targetSize.x;

            int imageCount = targetSize.x * targetSize.y;
            imageCount = imageCount > images.size() ? images.size() : imageCount;

            System.out.println("imageCount = " + imageCount);

            BufferedImage source;
            for(int i = 1; i <= imageCount; i++ ) {
                source = images.get(i - 1);
                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getHeight(); y++) {
                        Color color = new Color(source.getRGB(x, y));

                        // Получаем каналы этого цвета
                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();
                        int alpha = source.getRGB(x, y) >> 24 & 0xff;//костыль для получения альфы - color.getAlpha();
//
                        Color newColor = new Color(red, green, blue, alpha);

                        int a = 1;
                        result.setRGB(nodeResolution.x * ((i-1)%maxValuex) + x, nodeResolution.y * ((i-1)/maxValuex) + y, newColor.getRGB());
                    }
                }
            }

        File output1 = new File("src\\Images\\imagege.png");
        ImageIO.write(resizeImage(result, 1024, 1024), "png", output1);
//            ImageIO.write(result, "png", output);
        //System.out.println("Min = " + pathNameTo);
    } catch (IOException e) {

        // При открытии и сохранении файлов, может произойти неожиданный случай.
        // И на этот случай у нас try catch
        System.out.println("Файл не найден или не удалось сохранить");
    }
    }

    public BufferedImage getImage(){
        return result;
    }

    public static Point getNearestResolutionExpTwo(Point imageCount, Point imageResolution){
        int [] defaultResolution = { 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384};

        int i = 1;//просто чтоб было

        Point preTargetResolution = getTargetResolution( imageCount, imageResolution );
        Point resultPoint = new Point();

        for (int def : defaultResolution){
            if(preTargetResolution.x < def){
                resultPoint.x = def;
                break;
            }
        }

        for (int def : defaultResolution){
            if(preTargetResolution.y < def){
                resultPoint.y = def;
                break;
            }
        }

        return resultPoint;
    }


    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_AREA_AVERAGING);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static Point getTargetResolution(Point imageCount, Point imageResolution){
        return new Point (imageResolution.x * imageCount.x, imageResolution.y * imageCount.y);
    }
}
