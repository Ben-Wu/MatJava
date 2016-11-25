package ca.benwu;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by Ben Wu on 2016-11-22.
 */

public class Test {
    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{{7,8,3,0}, {8,9,3,0}, {6,9,9,7}, {1,4,7,5}});
        Matrix b = new Matrix(new double[][]{{6,8,5,1}, {3,5,6,6}, {7,3,0,2}, {1,4,9,1}});

        try {
            BufferedImage hugeImage = ImageIO.read(new File("C:\\Users\\bw964\\Dropbox\\School\\3A\\CS370\\a2\\house.jpg"));
            int[][][] arr = convertTo2DWithoutUsingGetRGB(hugeImage);

            File output = new File("output.jpg");

            int colours = arr.length;
            int width = arr[0][0].length;
            int height = arr[0].length;

            int[] newImage = new int[colours * width * height];

            for (int row = 0 ; row < height ; row++) {
                for (int col = 0; col < width; col++) {
                    for (int colour = 0; colour < colours; colour++) {
                        newImage[colours * (row * width + col) + colour] = 255 - arr[colour][row][col];
                    }
                }
            }

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            WritableRaster raster = image.getRaster();
            raster.setPixels(0,0,width,height,newImage);

            ImageIO.write(image, "jpg", output);

            System.out.print("done");
        } catch (IOException e) {
            System.out.print("failed");
        }
    }

    private static int[][][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        // first dimension is {a, r, g, b}
        int[][][] result = new int[hasAlphaChannel ? 4 : 3][height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                result[0][row][col] = (pixels[pixel] & 0xff); // alpha
                result[3][row][col] = (pixels[pixel + 1] & 0xff); // blue
                result[2][row][col] = (pixels[pixel + 2] & 0xff); // green
                result[1][row][col] = (pixels[pixel + 3] & 0xff); // red
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                result[2][row][col] = (pixels[pixel] & 0xff); // blue
                result[1][row][col] = (pixels[pixel + 1] & 0xff); // green
                result[0][row][col] = (pixels[pixel + 2] & 0xff); // red
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }
}
