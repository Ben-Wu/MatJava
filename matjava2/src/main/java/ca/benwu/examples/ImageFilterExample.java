package ca.benwu.examples;

import java.io.IOException;
import java.util.Scanner;

import ca.benwu.ImageProcessor;

/**
 * Created by Ben Wu on 8/3/2017.
 */

public class ImageFilterExample {

    private static double[][] filter = {
            {0,     0,      0,      0,      0,      0,      0,      0,      0},
            {0,     0,      0,      0,      0,      0,      0,      0,      0},
            {0,     0,      0,      0,      0,      0,      0,      0,      0},
            {0,     0,      0,      -1,      -1,      -1,      0,      0,      0},
            {0,     0,      0,      -1,      11,      -1,      0,      0,      0},
            {0,     0,      0,      -1,      -1,      -1,      0,      0,      0},
            {0,     0,      0,      0,      0,      0,      0,      0,      0},
            {0,     0,      0,      0,      0,      0,      0,      0,      0},
            {0,     0,      0,      0,      0,      0,      0,      0,      0}
    };

    private static double factor = 1.0 / 3;

    public static void main(String[] args) throws IOException {

        Scanner s = new Scanner(System.in);
        System.out.println("Input filename: ");

        String inputFilename = "output2.jpg";

        System.out.println("Output filename: ");

        String outputFilename = "output.jpg";

        long startTime = System.currentTimeMillis();

        int[][][] pixelData = ImageProcessor.readImage(inputFilename);
        int width = pixelData[0][0].length;
        int height = pixelData[0].length;

        System.out.println("Read time: " + (System.currentTimeMillis() - startTime));

        int[][][] outputData = new int[pixelData.length][pixelData[0].length][pixelData[0][0].length];

        for (int y = 0 ; y < height ; y++) {
            for (int x = 0; x < width ; x++) {
                double r = 0;
                double g = 0;
                double b = 0;

                // apply filter
                for (int filterY = 0 ; filterY < filter.length ; filterY++) {
                    for (int filterX = 0 ; filterX < filter[0].length; filterX++) {

                        int localX = x - filter.length / 2 + filterX;
                        int localY = y - filter[0].length / 2 + filterY;

                        if (localX >= 0 && localX < width && localY >= 0 && localY < height) {
                            r += filter[filterY][filterX] * pixelData[0][localY][localX];
                            g += filter[filterY][filterX] * pixelData[1][localY][localX];
                            b += filter[filterY][filterX] * pixelData[2][localY][localX];
                        }
                    }
                }

                outputData[0][y][x] = (int) (Math.max(0, Math.min(255, r * factor)));
                outputData[1][y][x] = (int) (Math.max(0, Math.min(255, g * factor)));
                outputData[2][y][x] = (int) (Math.max(0, Math.min(255, b * factor)));
            }
        }

        System.out.println("Filter time: " + (System.currentTimeMillis() - startTime));

        ImageProcessor.writeImage(outputData, outputFilename);

        System.out.println("Total time: " + (System.currentTimeMillis() - startTime));

    }

}
