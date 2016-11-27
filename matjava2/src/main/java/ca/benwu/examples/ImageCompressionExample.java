package ca.benwu.examples;

import java.io.IOException;
import java.util.Scanner;

import ca.benwu.Fourier;
import ca.benwu.ImageProcessor;
import ca.benwu.Matrix;

/**
 * Created by Ben Wu on 2016-11-26.
 */

public class ImageCompressionExample {

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Input filename: ");

            String inputFilename = "scene.jpg";

            System.out.println("Output filename: ");

            String outputFilename = "compressed.png";

            // read image pixel data
            int[][][] pixelData = ImageProcessor.readImage(inputFilename);

            final int BLOCK_SIZE = 16;
            final int COMPRESSED_BLOCK_SIZE = 8;

            int[][][] compressedPixelData = new int[pixelData.length][][];

            // fourier transform for each BLOCK_SIZE by BLOCK_SIZE block in each colour matrix
            for(int i = 0 ; i < pixelData.length ; i++) {
                System.out.println("Colour " + (i + 1));
                // create matrix representing pixel data for current colour
                Matrix colourMatrix = new Matrix(pixelData[i]);
                // matrix for pixel data after compression
                Matrix compressedMatrix = null;
                System.out.print("Compressing...");
                for(int row = 0 ; row + BLOCK_SIZE <= colourMatrix.getHeight() ; row += BLOCK_SIZE) {
                    System.out.print(".");
                    Matrix compressedRow = null;
                    for(int col = 0 ; col + BLOCK_SIZE <= colourMatrix.getWidth() ; col += BLOCK_SIZE) {
                        Matrix compressedBlock = Fourier.dct(colourMatrix.subMatrix(row, col, row + BLOCK_SIZE - 1, col + BLOCK_SIZE - 1))
                                .subMatrix(0,0,COMPRESSED_BLOCK_SIZE-1,COMPRESSED_BLOCK_SIZE-1);

                        compressedBlock = compressedBlock.horzcat(Matrix.zeros(COMPRESSED_BLOCK_SIZE,BLOCK_SIZE-COMPRESSED_BLOCK_SIZE))
                                .vertcat(Matrix.zeros(BLOCK_SIZE-COMPRESSED_BLOCK_SIZE,BLOCK_SIZE));

                        compressedBlock = Fourier.idct(compressedBlock);

                        if(compressedRow == null) {
                            compressedRow = compressedBlock;
                        } else {
                            compressedRow = compressedRow.horzcat(compressedBlock);
                        }
                    }
                    if(compressedMatrix == null) {
                        compressedMatrix = compressedRow;
                    } else {
                        compressedMatrix = compressedMatrix.vertcat(compressedRow);
                    }
                }
                System.out.println();
                compressedPixelData[i] = compressedMatrix.round();
            }

            ImageProcessor.writeImage(compressedPixelData, outputFilename);

            System.out.print("done");
        } catch (IOException e) {
            System.out.print("failed");
        }
    }

}
