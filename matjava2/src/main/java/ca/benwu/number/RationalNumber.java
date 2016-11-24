package ca.benwu.number;

/**
 * Created by Ben Wu on 2016-11-24.
 */

public class RationalNumber extends AbstractNumber {

    public RationalNumber(double val1, double val2) {
        super(val1, val2);
    }

    public AbstractNumber times(AbstractNumber n) {
        if(n instanceof ComplexNumber) {
            return new ComplexNumber(
                    ((ComplexNumber) n).getReal() * getNumerator() / getDenom(),
                    ((ComplexNumber) n).getReal() * getNumerator() / getDenom());
        } else if(n instanceof RationalNumber) {
            return new RationalNumber(getNumerator() * ((RationalNumber) n).getNumerator(),
                    getDenom() * ((RationalNumber) n).getDenom())
                    .simplify();
        } else {
            throw new IllegalArgumentException("Unrecognized number type");
        }
    }

    public RationalNumber simplify() {
        if(getNumerator() % 1 != 0 || getDenom() % 1 != 0) {
            return new RationalNumber(getNumerator(), getDenom());
        } else {
            int gcd = gcd((int)getNumerator(), (int)getDenom());
            return new RationalNumber(getNumerator()/gcd, getDenom()/gcd);
        }
    }

    public int gcd(int a, int b) {
        while(b != 0) {
            int t = b;
            b = a%b;
            a = t;
        }
        return a;
    }

    public double getNumerator() {
        return val1;
    }

    public double getDenom() {
        return val2;
    }

    @Override
    public String toString() {
        return val1 + "/" + val2;
    }
}
