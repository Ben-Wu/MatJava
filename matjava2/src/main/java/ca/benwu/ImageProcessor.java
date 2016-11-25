package ca.benwu;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Created by Ben Wu on 2016-11-25.
 */

/**
 *  Read and write images directly using arrays representing pixel data
 */
public class ImageProcessor {

    public static int[][][] readImage(String uri) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(uri));
        return getPixelData(inputImage);
    }

    public static void writeImage(int[][][] pixelData, String filename) throws IOException {
        int colours = pixelData.length;
        int width = pixelData[0][0].length;
        int height = pixelData[0].length;

        int[] newImage = new int[colours * width * height];

        for (int row = 0 ; row < height ; row++) {
            for (int col = 0; col < width; col++) {
                for (int colour = 0; colour < colours; colour++) {
                    newImage[colours * (row * width + col) + colour] = pixelData[colour][row][col];
                }
            }
        }

        BufferedImage image = new BufferedImage(width, height, colours == 4 ? BufferedImage.TYPE_4BYTE_ABGR : BufferedImage.TYPE_INT_BGR);
        WritableRaster raster = image.getRaster();
        raster.setPixels(0, 0, width, height, newImage);

        ImageIO.write(image, "png", new File(filename));
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
