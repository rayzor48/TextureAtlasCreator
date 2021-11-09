package logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

class ImageProcessor {
    public static void main(String[] args) {

        //System.out.println(getNearestResolutionExpTwo(new Point(2, 1), new Point(54, 154)));

        //               0  1  2  3  4  5  6  7  8  9  10 11 //   count_X = 3, count_Y = 4
        int [] massX1 = {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2};//   massX = i % count_X
        int [] massY1 = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3};//   massY = i / count_X

        //               0  1  2  3  4  5  6  7  8  9  10 11 //   count_X = 4, count_Y = 4
        int [] massX2 = {0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3};//   massX = i % count_X
        int [] massY2 = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2};//   massY = i / count_X

        int [] massX3 = {0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3};
        int [] massY3 = {0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3};


        int [] massX4 = {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2};
        int [] massY4 = {0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4};

        int [] massX5 = {0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4};
        int [] massY5 = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};


        try {
            String patternPathIn ="D:\\ForTheAtlasTexture\\01\\";
            String patternPathTo ="D:\\ForTheAtlasTexture\\Result\\";
            String fileName = "Circle (";//25)
            String fileNameTo = "res64.png";//Circle (25)

            Point nodeResolution = new Point(204, 204);
            Point targetSize = new Point(5, 5); //int colums = 6, lines = 6;

            int begin = 1, end = targetSize.x * targetSize.y;

            String sufficsName =").png";

            int min = 1000;

            String pathNameTo = patternPathTo + fileNameTo;//sufficsName;

            Point resultResolution = getTargetResolution(targetSize, nodeResolution);

            BufferedImage result = new BufferedImage(resultResolution.x, resultResolution.y, BufferedImage.TYPE_INT_ARGB);

            int maxValuex = targetSize.x > targetSize.y ? targetSize.x : targetSize.x;
            //int maxValuey = targetSize.x > targetSize.y ? targetSize.x : targetSize.y;

            for(int i = begin; i <= end; i++) {
                // Открываем изображение
//                System.out.println("PathIn = " + pathNameIn);

                // Делаем двойной цикл, чтобы обработать каждый пиксель
                String pathNameIn = patternPathIn + fileName + i + sufficsName;
                System.out.println(pathNameIn);

                File file = new File(pathNameIn);
                BufferedImage source = ImageIO.read(file);

                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getHeight(); y++) {
                        // Получаем цвет текущего пикселя
                        Color color = new Color(source.getRGB(x, y));

                        // Получаем каналы этого цвета
                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();
                        int alpha = color.getAlpha();//source.getRGB(x, y) >> 24 & 0xff;
//                              System.out.println("Red: " + color.getRed() + "  Green: " + color.getGreen() + " Blue: " + color.getBlue() + " Alpha: " + color.getAlpha() );//log output
                        //System.out.println(color);
                        // Применяем стандартный алгоритм для получения черно-белого изображения


                        // Если вы понаблюдаете, то заметите что у любого оттенка серого цвета, все каналы имеют
                        // одно и то же значение. Так, как у нас изображение тоже будет состоять из оттенков серого
                        // то, все канали будут иметь одно и то же значение.
                        //  Cоздаем новый цвет
                        Color newColor = new Color(red, green, blue, alpha);

                        // И устанавливаем этот цвет в текущий пиксель результирующего изображения
                        System.out.println("source = " + result.getWidth() + " i = " + (i-1) + " (i)/" + maxValuex + " = " + ((i-1)%maxValuex) +  " x = " + (nodeResolution.x * ((i-1)%maxValuex) + x) + " y = " + (nodeResolution.y * ((i-1)/ maxValuex) + y));
                        result.setRGB(nodeResolution.x * ((i-1)%maxValuex) + x, nodeResolution.y * ((i-1)/maxValuex) + y, newColor.getRGB());
//                        result.setRGB(nodeResolution.x * (massX1[i-1]) + x, nodeResolution.y * (massY1[i-1]) + y, newColor.getRGB());
//                        result.setRGB(nodeResolution.x * (i % (targetSize.x)) + x, nodeResolution.y * (i / (targetSize.y)) + y, newColor.getRGB());
//                        System.out.println("x = " + 204 * ((end - i)/5) + x + "  y = " + 204 * ((end - i)%5) + y);
                       //System.out.println(" i = " + i + "x = " + ((29 - end + i)/5) + "  y = " + ((29 - end + i)%5) );

                    }
//                    System.out.println("source = " + result.getHeight() + " i = " + (i-1) +  " x = " + (nodeResolution.x * ((i-1)%targetSize.x) + x) + " y = " + (nodeResolution.y * ((i-1) % targetSize.y) + 0));


                }
//                System.out.println();
//                System.out.print(" " + i );
//                if(i % targetSize.y == 0){
//                    System.out.println();
//                }

              //  System.out.println("i/4 = " + ((i-1)%3) + " y = " + ((i-1)/3) );
//                System.out.println(" i = " + (24 - end + i) + " x = " + ((24 - end + i)/5) + "  y = " + ((24 - end + i) % 5) );
            }
                        // Созраняем результат в новый файл
            File output = new File(pathNameTo);
            ImageIO.write(resizeImage(result, 1024, 1024), "png", output);
//            ImageIO.write(result, "png", output);
            //System.out.println("Min = " + pathNameTo);
        } catch (IOException e) {

            // При открытии и сохранении файлов, может произойти неожиданный случай.
            // И на этот случай у нас try catch
            System.out.println("Файл не найден или не удалось сохранить");
        }
    }

    public static Point getNearestResolutionExpTwo(Point imageCount, Point imageResolution){
        int [] defaultResolution = { 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384};

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

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_AREA_AVERAGING);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public static Point getTargetResolution(Point imageCount, Point imageResolution){
        return new Point (imageResolution.x * imageCount.x, imageResolution.y * imageCount.y);
    }
}