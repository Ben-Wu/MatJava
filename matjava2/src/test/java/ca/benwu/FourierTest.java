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

}
