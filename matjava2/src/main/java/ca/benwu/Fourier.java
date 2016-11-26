package ca.benwu;

import ca.benwu.number.ComplexNumber;

/**
 * Created by Ben Wu on 2016-11-25.
 */

public class Fourier {

    public static ComplexNumber rootOfUnity(int n) {
        return new ComplexNumber(Math.cos(2*Math.PI/n), Math.sin(2*Math.PI/n));
    }

}
