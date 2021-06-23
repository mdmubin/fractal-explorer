package main.java.model;

import main.java.utils.Constants;

public class Mandelbrot {
    public static int computeIterations(double real, double imaginary) {
        Complex c = new Complex(real, imaginary);
        Complex z = new Complex(real, imaginary);

        int iters = 0;
        while (z.modulusSquared() <= 4.0) {
            z = Complex.add(Complex.multiply(z, z), c);
            if (iters > Constants.MAX_ITER)
                return Constants.MAX_ITER;
            iters++;
        }
        // outside mandelbrot set
        return iters;
    }
}
