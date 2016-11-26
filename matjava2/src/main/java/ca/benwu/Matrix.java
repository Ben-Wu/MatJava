package ca.benwu;

import java.util.Arrays;

import ca.benwu.number.AbstractNumber;
import ca.benwu.number.ComplexNumber;

public class Matrix {

    final private int width;
    final private int height;
    final private ComplexNumber[][] values;

    //TODO: Solve, LU, determinant, n-dimensional

    public Matrix(ComplexNumber[][] values) {
        if(values.length > 0) {
            int measuredWidth = values[0].length;
            for(ComplexNumber[] row : values) {
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

        this.values = new ComplexNumber[height][width];

        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < width ; col++) {
                this.values[row][col] = new ComplexNumber(values[row][col]);
            }
        }

        if(width < 1 || height < 1) {
            throw new IllegalArgumentException("Dimensions must be non-zero");
        }

    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComplexNumber[] row : values) {
            for(ComplexNumber value : row) {
                stringBuilder.append(value.toString());
                stringBuilder.append("\t");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Matrix plus(Matrix b) {
        if(width != b.height) {
            throw new IllegalArgumentException("Incorrect matrix dimensions");
        }
        ComplexNumber[][] rows = new ComplexNumber[height][b.width];
        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < b.width ; col++) {
                rows[row][col] = values[row][col].plus(b.valueAt(row, col));
            }
        }
        return new Matrix(rows);
    }

    public Matrix times(Matrix b) {
        if(width != b.height) {
            throw new IllegalArgumentException("Incorrect matrix dimensions");
        }
        ComplexNumber[][] rows = new ComplexNumber[height][b.width];
        for(int row = 0 ; row < height ; row++) {
            for(int col = 0 ; col < b.width ; col++) {
                rows[row][col] = vectorDotProduct(getRow(row).transpose(), b.getColumn(col));
            }
        }
        return new Matrix(rows);
    }

    public Matrix transpose() {
        ComplexNumber[][] transposedValues = new ComplexNumber[width][height];

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
        return new Matrix(new ComplexNumber[][]{values[index]});
    }

    public Matrix getColumn(int index) {
        ComplexNumber[] column = new ComplexNumber[height];

        for (int row = 0; row < height; row++) {
            column[row] = values[row][index];
        }

        return new Matrix(new ComplexNumber[][]{column}).transpose();
    }

    public ComplexNumber[][] getValueArray() {
        return values;
    }

    public ComplexNumber valueAt(int row, int col) {
        return values[row][col];
    }

    public void setValue(int row, int col, ComplexNumber value) {
        values[row][col] = value;
    }

    public void setValue(int row, int col, double value) {
        values[row][col] = new ComplexNumber(value);
    }

    private static ComplexNumber vectorDotProduct(Matrix a, Matrix b) {
        if(a.getWidth() != 1 || b.getWidth() != 1 || a.getHeight() != b.getHeight()) {
            throw new IllegalArgumentException();
        }
        ComplexNumber sum = new ComplexNumber(0);
        for (int i = 0 ; i < a.height ; i++) {
            sum = sum.plus(a.valueAt(i, 0).times(b.valueAt(i, 0)));
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

    public Matrix horzcat(Matrix m) {
        if(m.getHeight() != getHeight()) {
            throw new IllegalArgumentException("Matrix heights do not match");
        }
        ComplexNumber[][] concatValues = new ComplexNumber[getHeight()][];

        for(int row = 0 ; row < getHeight() ; row++) {
            ComplexNumber[] concatRow = new ComplexNumber[m.getWidth() + getWidth()];
            for(int col = 0 ; col < getWidth() ; col++) {
                concatRow[col] = valueAt(row,col);
            }
            for(int col = getWidth() ; col < m.getWidth() + getWidth() ; col++) {
                concatRow[col] = m.valueAt(row,col - getWidth());
            }
            concatValues[row] = concatRow;
        }
        return new Matrix(concatValues);
    }

    public Matrix vertcat(Matrix m) {
        if(m.getWidth() != getWidth()) {
            throw new IllegalArgumentException("Matrix widths do not match");
        }
        ComplexNumber[][] concatValues = new ComplexNumber[getHeight() + m.getHeight()][];

        for(int row = 0 ; row < getHeight() ; row++) {
            concatValues[row] = getRow(row).getValueArray()[0];
        }
        for(int row = getHeight() ; row < getHeight() + m.getHeight() ; row++) {
            concatValues[row] = m.getRow(row - getHeight()).getValueArray()[0];
        }
        return new Matrix(concatValues);
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
