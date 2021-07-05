package main.java.model;

import main.java.utils.Constants;

public class Mandelbrot {
    public static Complex z_n = null;

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
