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
        Matrix f3 = new Matrix(new double[][] {{2, 6, 5, 8, 7, 9, 6, 3, 6, 5}});

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
        assertEquals(new Matrix(new ComplexNumber[][]{{
                        new ComplexNumber(57),
                        new ComplexNumber(-8.618,-4.9798),
                        new ComplexNumber(0.61803,3.5267),
                        new ComplexNumber(-6.382,0.44903),
                        new ComplexNumber(-1.618,-5.7063),
                        new ComplexNumber(-5),
                        new ComplexNumber(-1.618,5.7063),
                        new ComplexNumber(-6.382,-0.44903),
                        new ComplexNumber(0.61803,-3.5267),
                        new ComplexNumber(-8.618,4.9798)}}),
                Fourier.dft(f3));
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

    @Test
    public void extensionTest() {
        Matrix a = new Matrix(new double[][] {
                {0.48800,      0.24042,      0.76034,      0.65137},
                {0.49784,      0.68491,       0.5841,      0.74371},
                {0.93598,      0.83925,      0.40295,      0.30195},
                {0.38928,      0.97014,      0.51004,     0.089612},
                {0.11715,      0.21517,      0.49564,      0.82597}});
        Matrix b = new Matrix(new double[][] {
                {  0.488,      0.24042,      0.76034,      0.65137,      0.76034,      0.24042},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.11715,      0.21517,      0.49564,      0.82597,      0.49564,      0.21517},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491}});

        assertEquals(b, Fourier.extension(a));
    }

    @Test
    public void inverseExtensionTest() {
        Matrix a = new Matrix(new double[][] {
                {0.48800,      0.24042,      0.76034,      0.65137},
                {0.49784,      0.68491,       0.5841,      0.74371},
                {0.93598,      0.83925,      0.40295,      0.30195},
                {0.38928,      0.97014,      0.51004,     0.089612},
                {0.11715,      0.21517,      0.49564,      0.82597}});
        Matrix b = new Matrix(new double[][] {
                {  0.488,      0.24042,      0.76034,      0.65137,      0.76034,      0.24042},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.11715,      0.21517,      0.49564,      0.82597,      0.49564,      0.21517},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491}});

        assertEquals(a, Fourier.inverseExtension(b));
    }

    @Test
    public void dctTest() {
        Matrix a = new Matrix(new double[][] {
                {0.48800,      0.24042,      0.76034,      0.65137},
                {0.49784,      0.68491,       0.5841,      0.74371},
                {0.93598,      0.83925,      0.40295,      0.30195},
                {0.38928,      0.97014,      0.51004,     0.089612},
                {0.11715,      0.21517,      0.49564,      0.82597}});
        Matrix b = new Matrix(new double[][] {
                {  0.488,      0.24042,      0.76034,      0.65137,      0.76034,      0.24042},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.11715,      0.21517,      0.49564,      0.82597,      0.49564,      0.21517},
                {0.38928,      0.97014,      0.51004,     0.089612,      0.51004,      0.97014},
                {0.93598,      0.83925,      0.40295,      0.30195,      0.40295,      0.83925},
                {0.49784,      0.68491,       0.5841,      0.74371,       0.5841,      0.68491}});

        assertEquals(b, Fourier.extension(a));
    }
}
