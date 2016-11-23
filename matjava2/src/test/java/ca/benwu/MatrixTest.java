package ca.benwu;

import org.junit.Test;

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
        assertEquals(a.getRow(2), a2);
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
}
