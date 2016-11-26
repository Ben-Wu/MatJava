package ca.benwu.number;

/**
 * Created by Ben Wu on 2016-11-24.
 */

public class ComplexNumber extends AbstractNumber {

    public ComplexNumber(double val1, double val2) {
        super(val1, val2);
    }

    public ComplexNumber(double val1) {
        super(val1, 0);
    }

    public ComplexNumber times(AbstractNumber n) {
        if(n instanceof ComplexNumber) {
            return new ComplexNumber(
                    getReal() * ((ComplexNumber) n).getReal() - getImaginary() * ((ComplexNumber) n).getImaginary(),
                    getReal() * ((ComplexNumber) n).getImaginary() + getImaginary() * ((ComplexNumber) n).getReal());
        } else if(n instanceof RationalNumber) {
            return new ComplexNumber(
                    getReal() * ((RationalNumber) n).getNumerator() / ((RationalNumber) n).getDenom(),
                    getImaginary() * ((RationalNumber) n).getNumerator() / ((RationalNumber) n).getDenom());
        } else {
            throw new IllegalArgumentException("Unrecognized number type");
        }
    }

    public ComplexNumber times(double n) {
        return new ComplexNumber(getReal() * n, getImaginary() * n);
    }

    public ComplexNumber plus(AbstractNumber n) {
        if(n instanceof ComplexNumber) {
            return new ComplexNumber(
                    getReal() + ((ComplexNumber) n).getReal(),
                    getImaginary() + ((ComplexNumber) n).getImaginary());
        } else {
            throw new IllegalArgumentException("Unrecognized number type");
        }
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(getReal(), -getImaginary());
    }

    public ComplexNumber plus(double n) {
        return new ComplexNumber(getReal() + n, getImaginary());
    }

    public ComplexNumber minus(AbstractNumber n) {
        if(n instanceof ComplexNumber) {
            return new ComplexNumber(
                    getReal() - ((ComplexNumber) n).getReal(),
                    getImaginary() - ((ComplexNumber) n).getImaginary());
        } else {
            throw new IllegalArgumentException("Unrecognized number type");
        }
    }

    public ComplexNumber minus(double n) {
        return new ComplexNumber(getReal() - n, getImaginary());
    }

    // integer exponents only
    public ComplexNumber pow(int exponent) {
        if(exponent == 0) {
            return new ComplexNumber(1);
        }
        ComplexNumber c = new ComplexNumber(getReal(), getImaginary());
        for(int i = 1 ; i < exponent ; i++) {
            c = c.times(this);
        }
        return c;
    }

    public double getReal() {
        return Math.round(val1 * 10)/10.0;
    }

    public double getImaginary() {
        return Math.round(val2 * 10)/10.0;
    }

    @Override
    public String toString() {
        return getReal()
                + (getImaginary() == 0 ? "" : ((getImaginary() >= 0 ? " + " : " - ")
                        + Math.abs(getImaginary()) + "i"));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof ComplexNumber) && getReal() == ((ComplexNumber) o).getReal() && getImaginary() == ((ComplexNumber) o).getImaginary();
    }
}
