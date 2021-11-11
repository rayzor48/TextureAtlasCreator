package logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageEditor {
    private BufferedImage result;
    void createAtlas(ArrayList<BufferedImage> images, Point targetSize) {

            Point nodeResolution = new Point(images.get(0).getWidth(), images.get(0).getHeight());

            Point resultResolution = getTargetResolution(targetSize, nodeResolution);

            result = new BufferedImage(resultResolution.x, resultResolution.y, BufferedImage.TYPE_INT_ARGB);

            int maxValuex = targetSize.x > targetSize.y ? targetSize.x : targetSize.x;

            int i = 1;
            for(BufferedImage source : images) {
                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getHeight(); y++) {
                        Color color = new Color(source.getRGB(x, y));

                        // Получаем каналы этого цвета
                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();
                        int alpha = color.getAlpha();//source.getRGB(x, y) >> 24 & 0xff;
//
                        Color newColor = new Color(red, green, blue, alpha);

                        int a = 1;
                        result.setRGB(nodeResolution.x * ((i-1)%maxValuex) + x, nodeResolution.y * ((i-1)/maxValuex) + y, newColor.getRGB());
                    }

                }
                i++;
            }
        File output = new File("I:\\ForTheAtlasTexture\\Result\\res1111111121.png");
        try {
            ImageIO.write(result, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
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
