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
        Matrix f2 = new Matrix(new double[][] {{2,6,7,8,5,4,12,1}});

        assertEquals(new Matrix(new double[][]{{24,-4-2/Math.sqrt(2),2,-4+2/Math.sqrt(2),-4,-4+2/Math.sqrt(2),2,-4-2/Math.sqrt(2)}}),
                Fourier.dft(f));
        assertEquals(new Matrix(new ComplexNumber[][]{{
                new ComplexNumber(45),
                new ComplexNumber(-6.5355,-1.364),
                new ComplexNumber(-12,-1),
                new ComplexNumber(0.5355,-11.3640),
                new ComplexNumber(7),
                new ComplexNumber(0.5355,11.364),
                new ComplexNumber(-12,1),
                new ComplexNumber(-6.5355,1.364)}}),
                Fourier.dft(f2));
    }

    @Test
    public void dft2Test() {
        Matrix f = new Matrix(new double[][] {
                {208, 161, 244, 244},
                {231, 25, 246, 124},
                {32, 71, 40, 204},
                {233, 139, 248, 36}});

        assertEquals(new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(2486), new ComplexNumber(-74,212), new ComplexNumber(478), new ComplexNumber(-74,-212)},
                {new ComplexNumber(510,30), new ComplexNumber(174,-50), new ComplexNumber(250,-22), new ComplexNumber(-230,50)},
                {new ComplexNumber(-78), new ComplexNumber(-14,220), new ComplexNumber(-790), new ComplexNumber(-14,-220)},
                {new ComplexNumber(510,-30), new ComplexNumber(-230,-50), new ComplexNumber(250,22), new ComplexNumber(174,50)}}),
                Fourier.dft2(f));
    }

    @Test
    public void idftTest() {
        Matrix F = new Matrix(new double[][]{{24,-4-2/Math.sqrt(2),2,-4+2/Math.sqrt(2),-4,-4+2/Math.sqrt(2),2,-4-2/Math.sqrt(2)}});
        Matrix F2 = new Matrix(new ComplexNumber[][]{{
                new ComplexNumber(45),
                new ComplexNumber(-6.5355,-1.364),
                new ComplexNumber(-12,-1),
                new ComplexNumber(0.5355,-11.3640),
                new ComplexNumber(7),
                new ComplexNumber(0.5355,11.364),
                new ComplexNumber(-12,1),
                new ComplexNumber(-6.5355,1.364)}});

        assertEquals(new Matrix(new double[][] {{1,3,2,4,5,4,2,3}}), Fourier.idft(F));
        assertEquals(new Matrix(new double[][] {{2,6,7,8,5,4,12,1}}), Fourier.idft(F2));
    }

    @Test
    public void idft2Test() {
        Matrix F = new Matrix(new ComplexNumber[][]{
                {new ComplexNumber(2486), new ComplexNumber(-74,212), new ComplexNumber(478), new ComplexNumber(-74,-212)},
                {new ComplexNumber(510,30), new ComplexNumber(174,-50), new ComplexNumber(250,-22), new ComplexNumber(-230,50)},
                {new ComplexNumber(-78), new ComplexNumber(-14,220), new ComplexNumber(-790), new ComplexNumber(-14,-220)},
                {new ComplexNumber(510,-30), new ComplexNumber(-230,-50), new ComplexNumber(250,22), new ComplexNumber(174,50)}});

        assertEquals(new Matrix(new double[][] {
                        {208, 161, 244, 244},
                        {231, 25, 246, 124},
                        {32, 71, 40, 204},
                        {233, 139, 248, 36}}),
                Fourier.idft2(F));
    }
}
