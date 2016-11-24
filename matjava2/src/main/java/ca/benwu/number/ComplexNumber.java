package ca.benwu.number;

/**
 * Created by Ben Wu on 2016-11-24.
 */

public class ComplexNumber extends AbstractNumber {

    public ComplexNumber(double val1, double val2) {
        super(val1, val2);
    }

    public AbstractNumber times(AbstractNumber n) {
        if(n instanceof ComplexNumber) {
            return new ComplexNumber(
                    getReal() * ((ComplexNumber) n).getReal() - getImaginary() * ((ComplexNumber) n).getImaginary(),
                    getReal() * ((ComplexNumber) n).getImaginary() - getImaginary() * ((ComplexNumber) n).getReal());
        } else if(n instanceof RationalNumber) {
            return new ComplexNumber(
                    getReal() * ((RationalNumber) n).getNumerator() / ((RationalNumber) n).getDenom(),
                    getImaginary() * ((RationalNumber) n).getNumerator() / ((RationalNumber) n).getDenom());
        } else {
            throw new IllegalArgumentException("Unrecognized number type");
        }
    }

    public double getReal() {
        return val1;
    }

    public double getImaginary() {
        return val2;
    }

    @Override
    public String toString() {
        return getReal() + (val2 >= 0 ? " + " : " - ") + Math.abs(getImaginary());
    }
}
