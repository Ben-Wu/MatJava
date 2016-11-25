package ca.benwu.number;

/**
 * Created by Ben Wu on 2016-11-24.
 */
// TODO: only complex numbers are used right now
public abstract class AbstractNumber {

    protected double val1;
    protected double val2;

    public AbstractNumber(double val1, double val2) {
        this.val1 = val1;
        this.val2 = val2;
    }

    public AbstractNumber times(AbstractNumber n1, AbstractNumber n2, AbstractNumber... numbers) {
        AbstractNumber val = n1.times(n2);
        for (AbstractNumber num : numbers) {
            val = val.times(num);
        }
        return val;
    }

    public abstract AbstractNumber times(AbstractNumber n);
}
