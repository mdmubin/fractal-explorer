package main.java.model;

import main.java.utils.Constants;

public class Mandelbrot {
    // Static reference to the final value of z after a point was determined to be in the set or not
    public static Complex z_n = null;

    /**
     * Returns the number of iterations to determine whether a value is within the mandelbrot set or not
     */
    public static int computeIterations(double real, double imaginary) {
        Complex c = new Complex(real, imaginary);
        Complex z = new Complex(real, imaginary);

        int iters = 0;
        while (z.modulusSquared() <= 4.0) {
            z = Complex.add(Complex.multiply(z, z), c);
            if (iters == Constants.CURRENT_MAX_ITER) {
                break;
            }
            iters++;
        }
        z_n = z;
        return iters;
    }
}
