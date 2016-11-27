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

    private static int numberOfColours = 0;

    private static long startTime;

    private static int counter = 0;

    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(System.in);
            System.out.println("Input filename: ");

            String inputFilename = s.next();

            System.out.println("Output filename: ");

            String outputFilename = s.next();

            // read image pixel data
            final int[][][] pixelData = ImageProcessor.readImage(inputFilename);

            numberOfColours = pixelData.length;

            final int BLOCK_SIZE = 16;
            final int COMPRESSED_BLOCK_SIZE = 8;

            final int[][][] compressedPixelData = new int[pixelData.length][][];

            startTime = System.currentTimeMillis();

            System.out.print("Compressing...");

            // fourier transform for each BLOCK_SIZE by BLOCK_SIZE block in each colour matrix
            for(int i = 0 ; i < pixelData.length ; i++) {
                final int colourIndex = i;
                // multi threading for much better performance
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // create matrix representing pixel data for current colour
                        Matrix colourMatrix = new Matrix(pixelData[colourIndex]);
                        // matrix for pixel data after compression
                        Matrix compressedMatrix = null;
                        // run algorithm on each block
                        for(int row = 0 ; row + BLOCK_SIZE <= colourMatrix.getHeight() ; row += BLOCK_SIZE) {
                            System.out.print(".");
                            Matrix compressedRow = null;
                            for(int col = 0 ; col + BLOCK_SIZE <= colourMatrix.getWidth() ; col += BLOCK_SIZE) {
                                // perform dct and take only the first COMPRESSED_BLOCK_SIZE coeffiecients
                                Matrix compressedBlock = Fourier.dct(colourMatrix.subMatrix(row, col, row + BLOCK_SIZE - 1, col + BLOCK_SIZE - 1))
                                        .subMatrix(0,0,COMPRESSED_BLOCK_SIZE-1,COMPRESSED_BLOCK_SIZE-1);

                                // zero-pad sub-matrix
                                compressedBlock = compressedBlock.horzcat(Matrix.zeros(COMPRESSED_BLOCK_SIZE,BLOCK_SIZE-COMPRESSED_BLOCK_SIZE))
                                        .vertcat(Matrix.zeros(BLOCK_SIZE-COMPRESSED_BLOCK_SIZE,BLOCK_SIZE));

                                // convert back to regular pixel array
                                compressedBlock = Fourier.idct(compressedBlock);

                                // add compressed block to row
                                if(compressedRow == null) {
                                    compressedRow = compressedBlock;
                                } else {
                                    compressedRow = compressedRow.horzcat(compressedBlock);
                                }
                            }
                            // add compressed row to matrix
                            if(compressedMatrix == null) {
                                compressedMatrix = compressedRow;
                            } else {
                                compressedMatrix = compressedMatrix.vertcat(compressedRow);
                            }
                        }
                        compressedPixelData[colourIndex] = new Matrix(compressedMatrix.round()).limit(0, 255);
                        counter++;
                    }
                }).start();
            }

            while(counter < numberOfColours - 1) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }

            ImageProcessor.writeImage(compressedPixelData, outputFilename);

            System.out.println();
            System.out.println("done: " + (System.currentTimeMillis() - startTime));
        } catch (IOException e) {
            System.out.println();
            System.out.println("failed");
        }
    }
}
