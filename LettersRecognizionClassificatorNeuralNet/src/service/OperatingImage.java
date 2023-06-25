package service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OperatingImage {

 /*   private List<Double> inputLearningImage = new ArrayList<>();
    private List<Double> outputLearningImage = new ArrayList<>();*/

    public List<List<Double>> learnInputData = new ArrayList<>();
    public List<List<Double>> learnOutputData = new ArrayList<>();


    public OperatingImage(Character character) throws IOException, InterruptedException {

        File folder = new File("images/" + character + "/");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                learnInputData.add(grabPixels(readFromFile(file)));
            }
        }

       /* while (readFromFile("images/" + character + "/" + character + "*.png") != null){
            String fileName = "images/" + character + "/"  + character + "*.png";
            learnInputData.add(grabPixels(readFromFile(fileName)));
        }*/
    }

    public OperatingImage(String [] fileName) throws IOException, InterruptedException {
        for(int i = 0; i < fileName.length; i++) {
            learnInputData.add(grabPixels(readFromFile(fileName[i])));
        }
    }


    public BufferedImage readFromFile(File file) throws IOException {
        //File sourceimage = new File("images/a/" + fileName);
        BufferedImage image = ImageIO.read(file);
        return image;
    }

    public BufferedImage readFromFile(String fileName) throws IOException {
        File sourceimage = new File("images/a/" + fileName);
        BufferedImage image = ImageIO.read(sourceimage);
        return image;
    }


    public List<Double> grabPixels(BufferedImage image) throws InterruptedException {

        List<Double> arr = new ArrayList<>();
        int[] pixels;
        int width1 = image.getWidth();
        int height1 = image.getHeight();
        pixels = new int[width1 * height1];
        PixelGrabber pgb = new PixelGrabber(image, 0, 0, width1, height1, pixels, 0, width1);
        pgb.grabPixels();
        int[][] pixels2D = new int[width1][height1];

        for(int y = 0; y < height1; y++) {
            for(int x = 0; x < width1; x++) {
                pixels2D[x][y] = pixels[y * width1 + x];

             //   arr.add((pixels2D[x][y])*1.0d);

                if (Math.abs(pixels2D[x][y] * 1.0d ) > 1d){
                    arr.add(0.99d);
                } else {
                    arr.add((0.01d));
                }
            }
        }
          return arr;
    }



}
