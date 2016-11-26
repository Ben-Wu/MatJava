package ca.benwu;

import ca.benwu.number.ComplexNumber;

/**
 * Created by Ben Wu on 2016-11-25.
 */

public class Fourier {

    public static ComplexNumber rootOfUnity(int n) {
        return new ComplexNumber(Math.cos(2*Math.PI/n), Math.sin(2*Math.PI/n));
    }

    // only ffts the first row
    public static Matrix dft(Matrix f) {
        ComplexNumber[] F = fft(f.getValueArray()[0]);

        return new Matrix(new ComplexNumber[][] {F});
    }

    // x.length must be a power of 2
    public static ComplexNumber[] fft(ComplexNumber[] f) {
        int n = f.length;

        if (n == 1) return new ComplexNumber[] { f[0] };

        ComplexNumber[] even = new ComplexNumber[n/2];
        ComplexNumber[] odd  = new ComplexNumber[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = f[2*k];
            odd[k] = f[2*k + 1];
        }
        even = fft(even);
        odd = fft(odd);

        ComplexNumber[] F = new ComplexNumber[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            ComplexNumber wk = new ComplexNumber(Math.cos(kth), Math.sin(kth));
            F[k] = even[k].plus(wk.times(odd[k]));
            F[k+n/2] = even[k].minus(wk.times(odd[k]));
        }
        return F;
    }

    public static Matrix dft2(Matrix f) {
        Matrix F;
        ComplexNumber[][] Fvalues = new ComplexNumber[f.getHeight()][];
        for(int row = 0 ; row < f.getHeight() ; row++) {
            Fvalues[row] = fft(f.getValueArray()[row]);
        }
        F = new Matrix(Fvalues).transpose();
        for(int row = 0 ; row < F.getHeight() ; row++) {
            Fvalues[row] = fft(F.getValueArray()[row]);
        }
        F = new Matrix(Fvalues).transpose();

        return F;
    }


    public static Matrix idft(Matrix F) {
        ComplexNumber[] f = ifft(F.getValueArray()[0]);

        return new Matrix(new ComplexNumber[][] {f});
    }

    // x.length must be a power of 2
    public static ComplexNumber[] ifft(ComplexNumber[] F) {
        int n = F.length;

        ComplexNumber[] f = new ComplexNumber[n];
        for (int i = 0; i < n; i++) {
            f[i] = F[i].conjugate();
        }
        f = fft(f);
        for (int i = 0; i < n; i++) {
            f[i] = f[i].conjugate().times(1.0 / n);
        }

        return f;
    }

    public static Matrix idft2(Matrix F) {
        Matrix f;
        ComplexNumber[][] fvalues = new ComplexNumber[F.getHeight()][];
        for(int row = 0 ; row < F.getHeight() ; row++) {
            fvalues[row] = ifft(F.getValueArray()[row]);
        }
        f = new Matrix(fvalues).transpose();
        for(int row = 0 ; row < f.getHeight() ; row++) {
            fvalues[row] = ifft(f.getValueArray()[row]);
        }
        f = new Matrix(fvalues).transpose();

        return f;
    }

    public static Matrix extension(Matrix m) {
        Matrix extension = m;
        for(int col = m.getWidth() - 2 ; col > 0 ; col--) {
            extension = extension.horzcat(m.getColumn(col));
        }
        for(int row = m.getHeight() - 2 ; row > 0 ; row--) {
            extension = extension.vertcat(extension.getRow(row));
        }
        return extension;
    }

    // assumes m is an extension
    public static Matrix inverseExtension(Matrix m) {
        return m.subMatrix(0,0,m.getHeight()/2,m.getWidth()/2);
    }

}
