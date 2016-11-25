package ca.benwu;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Ben Wu on 2016-11-22.
 */

public class Test {
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Input filename: ");

            String inputFilename = s.next();

            System.out.println("Output filename: ");

            String outputFilename = s.next();

            int[][][] pixelData = ImageProcessor.readImage(inputFilename);

            ImageProcessor.writeImage(pixelData, outputFilename);

            System.out.print("done");
        } catch (IOException e) {
            System.out.print("failed");
        }
    }
}
