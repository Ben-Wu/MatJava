package ca.benwu;

import org.junit.Test;

import ca.benwu.number.ComplexNumber;

import static org.junit.Assert.*;

/**
 * Created by Ben Wu on 2016-11-22.
 */

public class MatrixTest {
    @Test
    public void createRandom() {
        Matrix a = Matrix.random(4,6);
        assertEquals(a.getHeight(), 4);
        assertEquals(a.getWidth(), 6);

        Matrix b = Matrix.random(1,1);
        assertEquals(b.getHeight(), 1);
        assertEquals(b.getWidth(), 1);

        Matrix c = Matrix.random(100,323, 9);
        assertEquals(c.getHeight(), 100);
        assertEquals(c.getWidth(), 323);
    }

    @Test
    public void createNormal() {
        Matrix a = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        assertEquals(a.getHeight(), 4);
        assertEquals(a.getWidth(), 4);

        Matrix b = new Matrix(new double[][]{
                {7,8,3,0,6},
                {8,9,3,0,1},
                {6,9,9,7,0}});
        assertEquals(b.getHeight(), 3);
        assertEquals(b.getWidth(), 5);

        Matrix c = new Matrix(new double[][]{{1}});
        assertEquals(c.getHeight(), 1);
        assertEquals(c.getWidth(), 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void zeroSizeConstructorException() {
        Matrix a = new Matrix(new double[][] {});
    }

    @Test(expected=IllegalArgumentException.class)
    public void zeroSizeConstructorException2() {
        Matrix a = new Matrix(new double[][] {{}, {}});
    }

    @Test(expected=IllegalArgumentException.class)
    public void zeroSizeConstructorException3() {
        Matrix a = Matrix.random(5,0,43);
    }

    @Test(expected=IllegalArgumentException.class)
    public void zeroSizeConstructorException4() {
        Matrix a = Matrix.random(0,3);
    }

    @Test(expected=IllegalArgumentException.class)
    public void incorrectWidths() {
        Matrix a = new Matrix(new double[][] {{34, 54, 54, 5}, {54, 12, 43, 56}, {5, 45, 54, 33, 5}});
    }

    @Test
    public void testEquals() {
        Matrix a = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        Matrix b = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        assertEquals(a, b);
        assertEquals(b, a);

        Matrix c = new Matrix(new double[][]{
                {7,8,3,0,6},
                {8,9,3,0,1},
                {6,9,9,7,0}});
        Matrix d = new Matrix(new double[][]{
                {7,8,3,0,6},
                {8,9,3,0,1},
                {6,9,9,7,0}});
        assertEquals(c, d);
        assertEquals(d, c);

        Matrix e = new Matrix(new double[][]{{1}});
        Matrix f = new Matrix(new double[][]{{1}});
        assertEquals(e, f);
        assertEquals(f, e);

        Matrix g = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,2},
                {6,9,9,7},
                {1,4,7,5}});
        Matrix h = new Matrix(new double[][]{{0}});

        assertNotEquals(a,c);
        assertNotEquals(e,a);
        assertNotEquals(e,h);
        assertNotEquals(b,g);
        assertNotEquals(e,h);
    }

    @Test
    public void getters() {
        Matrix a = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        Matrix a1 = new Matrix(new double[][]{
                {8,9,3,0}});
        Matrix a2 = new Matrix(new double[][]{
                {1,4,7,5}});
        Matrix a0 = new Matrix(new double[][]{
                {7},
                {8},
                {6},
                {1}});
        Matrix a3 = new Matrix(new double[][]{
                {0},
                {0},
                {7},
                {5}});

        assertEquals(a.getRow(1), a1);
        assertEquals(a.getRow(3), a2);
        assertEquals(a.getColumn(0), a0);
        assertEquals(a.getColumn(3), a3);
    }

    @Test
    public void multiplication() {
        Matrix a = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        Matrix b = new Matrix(new double[][]{
                {6,8,5,1},
                {3,5,6,6},
                {7,3,0,2},
                {1,4,9,1}});
        Matrix ab = new Matrix(new double[][]{
                {87,105,83,61},
                {96,118,94,68},
                {133,148,147,85},
                {72,69,74,44}});
        Matrix ba = new Matrix(new double[][]{
                {137,169,94,40},
                {103,147,120,72},
                {75,91,44,10},
                {94,129,103,68}});

        assertEquals(a.times(b), ab);
        assertEquals(b.times(a), ba);
    }

    @Test
    public void addition() {
        Matrix a = new Matrix(new double[][]{
                {7,8,3,0},
                {8,9,3,0},
                {6,9,9,7},
                {1,4,7,5}});
        Matrix b = new Matrix(new double[][]{
                {6,8,5,1},
                {3,5,6,6},
                {7,3,0,2},
                {1,4,9,1}});
        Matrix ab = new Matrix(new double[][]{
                {13,16,8, 1},
                {11,14,9,6},
                {13,12,9,9},
                {2,8,16,6}});

        assertEquals(a.plus(b), ab);
        assertEquals(b.plus(a), ab);
    }

    @Test
    public void complexMultiplication() {
        Matrix a = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(1, 2), new ComplexNumber(4, 0), new ComplexNumber(4, 3), new ComplexNumber(5, 0)},
                {new ComplexNumber(1, 4), new ComplexNumber(10, 1), new ComplexNumber(8, 3), new ComplexNumber(5, 1)},
                {new ComplexNumber(9, 5), new ComplexNumber(1, 6), new ComplexNumber(8, 3), new ComplexNumber(7, -2)},
                {new ComplexNumber(7, 0), new ComplexNumber(5, 7), new ComplexNumber(2, 3), new ComplexNumber(8, -7)}});
        Matrix b = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(8, 9), new ComplexNumber(2, 3), new ComplexNumber(3, 3), new ComplexNumber(3, -3)},
                {new ComplexNumber(3, 5), new ComplexNumber(2, 8), new ComplexNumber(9, 9), new ComplexNumber(6, 6)},
                {new ComplexNumber(7, 2), new ComplexNumber(5, 3), new ComplexNumber(4, -4), new ComplexNumber(5, 5)},
                {new ComplexNumber(7, 2), new ComplexNumber(10, 8), new ComplexNumber(2, 2), new ComplexNumber(4, -4)}});
        Matrix ab = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(59, 84), new ComplexNumber(65, 106), new ComplexNumber(71, 51), new ComplexNumber(58, 42)},
                {new ComplexNumber(80, 148), new ComplexNumber(75, 182), new ComplexNumber(124, 106), new ComplexNumber(118, 114)},
                {new ComplexNumber(103, 181), new ComplexNumber(74, 132), new ComplexNumber(29, 95), new ComplexNumber(57, 49)},
                {new ComplexNumber(114, 101), new ComplexNumber(105, 90), new ComplexNumber(53, 135), new ComplexNumber(8, 16)}});
        Matrix ba = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(13, 57), new ComplexNumber(70, 95), new ComplexNumber(42, 126), new ComplexNumber(77, 32)},
                {new ComplexNumber(41, 195), new ComplexNumber(-33, 237), new ComplexNumber(28, 228), new ComplexNumber(188, 118)},
                {new ComplexNumber(87, 58), new ComplexNumber(93, 123), new ComplexNumber(92, 73), new ComplexNumber(152, -1)},
                {new ComplexNumber(17, 64), new ComplexNumber(158, 120), new ComplexNumber(108, 149), new ComplexNumber(99, 10)}});

        assertEquals(a.times(b), ab);
        assertEquals(b.times(a), ba);
    }

    @Test
    public void complexAddition() {
        Matrix a = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(1, 2), new ComplexNumber(4, 0), new ComplexNumber(4, 3), new ComplexNumber(5, 0)},
                {new ComplexNumber(1, 4), new ComplexNumber(10, 1), new ComplexNumber(8, 3), new ComplexNumber(5, 1)},
                {new ComplexNumber(9, 5), new ComplexNumber(1, 6), new ComplexNumber(8, 3), new ComplexNumber(7, -2)},
                {new ComplexNumber(7, 0), new ComplexNumber(5, 7), new ComplexNumber(2, 3), new ComplexNumber(8, -7)}});
        Matrix b = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(8, 9), new ComplexNumber(2, 3), new ComplexNumber(3, 3), new ComplexNumber(3, -3)},
                {new ComplexNumber(3, 5), new ComplexNumber(2, 8), new ComplexNumber(9, 9), new ComplexNumber(6, 6)},
                {new ComplexNumber(7, 2), new ComplexNumber(5, 3), new ComplexNumber(4, -4), new ComplexNumber(5, 5)},
                {new ComplexNumber(7, 2), new ComplexNumber(10, 8), new ComplexNumber(2, 2), new ComplexNumber(4, -4)}});
        Matrix ab = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(9, 11), new ComplexNumber(6, 3), new ComplexNumber(7, 6), new ComplexNumber(8, -3)},
                {new ComplexNumber(4, 9), new ComplexNumber(12, 9), new ComplexNumber(17, 12), new ComplexNumber(11, 7)},
                {new ComplexNumber(16, 7), new ComplexNumber(6, 9), new ComplexNumber(12, -1), new ComplexNumber(12, 3)},
                {new ComplexNumber(14, 2), new ComplexNumber(15, 15), new ComplexNumber(4, 5), new ComplexNumber(12, -11)}});

        assertEquals(a.plus(b), ab);
        assertEquals(b.plus(a), ab);
    }
}
