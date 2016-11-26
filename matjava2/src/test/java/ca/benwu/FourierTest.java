package ca.benwu;

import org.junit.Test;

import static org.junit.Assert.*;

import ca.benwu.number.ComplexNumber;

/**
 * Created by Ben Wu on 2016-11-25.
 */

public class FourierTest {

    @Test
    public void unityTest() {
        ComplexNumber w4 = Fourier.rootOfUnity(4);

        assertEquals(new ComplexNumber(0,1), w4);
        assertEquals(new ComplexNumber(1), w4.pow(0));
        assertEquals(new ComplexNumber(0,1), w4.pow(1));
        assertEquals(new ComplexNumber(-1), w4.pow(2));
        assertEquals(new ComplexNumber(0,-1), w4.pow(3));
        assertEquals(new ComplexNumber(1), w4.pow(4));
        assertEquals(new ComplexNumber(0,1), w4.pow(5));

        ComplexNumber w8 = Fourier.rootOfUnity(8);

        assertEquals(new ComplexNumber(Math.sqrt(0.5),Math.sqrt(0.5)), w8);
        assertEquals(new ComplexNumber(1), w8.pow(0));
        assertEquals(new ComplexNumber(Math.sqrt(0.5),Math.sqrt(0.5)), w8.pow(1));
        assertEquals(new ComplexNumber(0,1), w8.pow(2));
        assertEquals(new ComplexNumber(-Math.sqrt(0.5),Math.sqrt(0.5)), w8.pow(3));
        assertEquals(new ComplexNumber(-1), w8.pow(4));
        assertEquals(new ComplexNumber(-Math.sqrt(0.5),-Math.sqrt(0.5)), w8.pow(5));
        assertEquals(new ComplexNumber(0,-1), w8.pow(6));
        assertEquals(new ComplexNumber(Math.sqrt(0.5),-Math.sqrt(0.5)), w8.pow(7));
        assertEquals(new ComplexNumber(1), w8.pow(8));
        assertEquals(new ComplexNumber(Math.sqrt(0.5),Math.sqrt(0.5)), w8.pow(9));
    }

    @Test
    public void dftTest() {
        Matrix f = new Matrix(new double[][] {{1,3,2,4,5,4,2,3}});
        Matrix F = Fourier.dft(f);

        Matrix expected = new Matrix(new double[][]{{24,-4-2/Math.sqrt(2),2,-4+2/Math.sqrt(2),-4,-4+2/Math.sqrt(2),2,-4-2/Math.sqrt(2)}});

        assertEquals(expected, F);
    }

    @Test
    public void idftTest() {
        Matrix F = new Matrix(new double[][]{{24,-4-2/Math.sqrt(2),2,-4+2/Math.sqrt(2),-4,-4+2/Math.sqrt(2),2,-4-2/Math.sqrt(2)}});
        Matrix f = Fourier.idft(F);

        Matrix expected = new Matrix(new double[][] {{1,3,2,4,5,4,2,3}});

        assertEquals(expected, f);
    }

}
