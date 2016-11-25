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
        try {
            BufferedImage inputImage = ImageIO.read(new File("house.jpg"));
            boolean hasAlphaChannel = inputImage.getAlphaRaster() != null;
            int[][][] arr = getPixelData(inputImage);

            int colours = arr.length;
            int width = arr[0][0].length;
            int height = arr[0].length;

            int[] newImage = new int[colours * width * height];

            for (int row = 0 ; row < height ; row++) {
                for (int col = 0; col < width; col++) {
                    for (int colour = 0; colour < colours; colour++) {
                        newImage[colours * (row * width + col) + colour] = arr[colour][row][col];
                    }
                }
            }

            BufferedImage image = new BufferedImage(width, height, hasAlphaChannel ? BufferedImage.TYPE_4BYTE_ABGR : BufferedImage.TYPE_INT_BGR);
            WritableRaster raster = image.getRaster();
            raster.setPixels(0, 0, width, height, newImage);

            ImageIO.write(image, "png", new File("compressed.png"));

            System.out.print("done");
        } catch (IOException e) {
            System.out.print("failed");
        }
    }

    private static int[][][] getPixelData(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        // first dimension is {a, r, g, b}
        int[][][] result = new int[hasAlphaChannel ? 4 : 3][height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                result[3][row][col] = (pixels[pixel] & 0xff); // alpha
                result[2][row][col] = (pixels[pixel + 1] & 0xff); // blue
                result[1][row][col] = (pixels[pixel + 2] & 0xff); // green
                result[0][row][col] = (pixels[pixel + 3] & 0xff); // red

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
