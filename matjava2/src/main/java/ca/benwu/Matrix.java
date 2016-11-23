package ca.benwu;

import java.sql.Array;
import java.util.Arrays;

public class Matrix {

    private int width;
    private int height;
    private double[][] values;

    public Matrix(int width, int height, double[][] values) {
        this.width = width;
        this.height = height;
        this.values = values;
    }

    public Matrix(double[][] values) {
        if(values.length > 0) {
            int measuredWidth = values[0].length;
            for(double[] row : values) {
                if(measuredWidth != row.length) {
                    throw new IllegalArgumentException("Incorrect matrix dimensions");
                }
            }
            this.width = measuredWidth;
        } else {
            this.width = 0;
        }
        this.height = values.length;
        this.values = values;

        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("Dimensions must be non-zero");
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (double[] row : values) {
            for(double value : row) {
                stringBuilder.append(value);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Matrix times(Matrix b) {
        if(width != b.height) {
            throw new IllegalArgumentException("Incorrect matrix dimensions");
        }
        double[][] rows = new double[height][b.width];
        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < b.width ; col++) {
                rows[row][col] = vectorDotProduct(getRow(row).transpose(), b.getColumn(col));
            }
        }
        return new Matrix(rows);
    }

    public Matrix transpose() {
        double[][] transposedValues = new double[width][height];

        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < width ; col++) {
                transposedValues[col][row] = values[row][col];
            }
        }

        return new Matrix(transposedValues);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Matrix getRow(int index) {
        return new Matrix(new double[][]{values[index]});
    }

    public Matrix getColumn(int index) {
        double[] column = new double[height];

        for (int row = 0; row < height; row++) {
            column[row] = values[row][index];
        }

        return new Matrix(new double[][]{column}).transpose();
    }

    public double valueAt(int row, int col) {
        return values[row][col];
    }

    private static int vectorDotProduct(Matrix a, Matrix b) {
        if(a.getWidth() != 1 || b.getWidth() != 1 || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException();
        }
        int sum = 0;
        for (int i = 0 ; i < a.height ; i++) {
            sum += a.valueAt(i, 0) * b.valueAt(i, 0);
        }
        return sum;
    }

    public static Matrix random(int height, int width) {
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("Dimensions must be non-zero");
        }

        double[][] values = new double[height][width];

        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < width ; col++) {
                values[row][col] = Math.random();
            }
        }
        return new Matrix(values);
    }

    public static Matrix random(int height, int width, int max) {
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("Dimensions must be non-zero");
        }

        double[][] values = new double[height][width];

        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < width ; col++) {
                values[row][col] = Math.floor(Math.random() * max);
            }
        }
        return new Matrix(values);
    }

    @Override
    public boolean equals(Object o) {
        if((o instanceof Matrix)
                && height == ((Matrix) o).getHeight()
                && width == ((Matrix) o).getWidth()) {
            for (int row = 0 ; row < height ; row++) {
                if(!Arrays.equals(values[row], ((Matrix) o).values[row])) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
