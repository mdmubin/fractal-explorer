package main.java.model;

/**
 * Simple Implementation of Complex Numbers
 */
public class Complex {
    final double re;
    final double im;


    public Complex() {
        re = im = 0.;
    }

    public Complex(double real, double imaginary) {
        this.re = real;
        this.im = imaginary;
    }


    public double modulusSquared() {
        return re * re + im * im;
    }

    public double modulus() {
        return Math.sqrt(modulusSquared());
    }

    public static Complex multiply(Complex z1, Complex z2) {
        // (a + bj) (x + yj) = (ax - by) + (ay + bx)j
        return new Complex(z1.re * z2.re - z1.im * z2.im, z1.re * z2.im + z1.im * z2.re);
    }

    public static Complex add(Complex z1, Complex z2) {
        // (a + bj) + (x + yj) = (a + x) + (b + y)j
        return new Complex(z1.re + z2.re, z1.im + z2.im);
    }
}
