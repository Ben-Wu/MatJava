package ca.benwu;

/**
 * Created by Ben Wu on 2016-11-22.
 */

public class Test {
    public static void main(String[] args) {
        Matrix a = new Matrix(new double[][]{{7,8,3,0}, {8,9,3,0}, {6,9,9,7}, {1,4,7,5}});
        Matrix b = new Matrix(new double[][]{{6,8,5,1}, {3,5,6,6}, {7,3,0,2}, {1,4,9,1}});

        System.out.print(a.toString());
    }
}
