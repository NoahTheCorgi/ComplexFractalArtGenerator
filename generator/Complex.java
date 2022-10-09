// NoahTheCorgi
// discrete modeling of complex field and its arithmetics::

package generator;
//import java.io.*;

public class Complex {

    public static void main(String[] args) {
        System.out.println("testing complex class");
        Complex a = new Complex(1, 1);
        Complex b = new Complex(1, 2);
        System.out.println(a.print());
        System.out.println(b.print());
        a.multiply(b);
        System.out.println(a.print());
    }

    // real and imaginary parts of a complex number
    public double real;
    public double imaginary;

    // basic constructors
    public Complex() {
        // empty arguments create a zero complex number
        this.real = 0;
        this.imaginary = 0;
    }

    // standard constructor
    public Complex(double x, double y) {
         this.real = x;
         this.imaginary = y;
    }

    // note that operations are done in place
    // this is to optimize on the memory side of things

    // basic operations::

    public void add(Complex z) {
        real = real + z.real;
        imaginary = imaginary + z.imaginary;
    }

    public void subtract(Complex z) {
        real = real - z.real;
        imaginary = imaginary - z.imaginary;
    }

    public void multiply(Complex z) {
        real = real*z.real - imaginary*z.imaginary;
        imaginary = z.imaginary*real + z.real*imaginary;
    }

    public void divide(Complex z) {
        // multiply conjugate of denominator on both top and bottom for normalized form::
        double normalizedFactor = (z.real*z.real) + (z.imaginary*z.imaginary);
        //Complex helper = multiply(new Complex(z.real, -(z.imaginary)));
        multiply(new Complex(z.real, -(z.imaginary)));
        real = real/normalizedFactor;
        imaginary = imaginary/normalizedFactor;
    }

    public void toThePowerOfInteger(int n) {
        Complex copy = new Complex(real, imaginary);
        for (int i=0; i<n-1; i++) {
            // n-1 more times of multiplications::
            multiply(copy);
        }
    }

    // Generalized operator for computing complex exponents::
    public void toThePowerOf(Complex z) {
        // (a + bi)^(c + di)
        double r = Math.sqrt((double) (real*real + imaginary*imaginary));
        double theta = findThetaRadians();
        double newRadius = Math.exp(Math.log(r) - (theta*z.imaginary));
        double alpha = (z.real * theta) + (z.imaginary * Math.log(r));
        real = newRadius * Math.cos(alpha);
        imaginary = newRadius * Math.sin(alpha);
    }

    public double findThetaRadians() {
        // need to double check for bugs in this section later
        double thetaRadians = 0.0;
        double r = Math.sqrt(real*real + imaginary*imaginary);
        // first quad
        if (real>=0 && imaginary>=0) {
            thetaRadians = Math.acos(real/r);
        }
        // second quad
        else if (real<=0 && imaginary>=0) {
            thetaRadians = Math.acos(real/r);
        }
        // third quad
        else if (real<=0 && imaginary<=0) {
            thetaRadians = Math.acos(real/r);
            thetaRadians += Math.PI;
        }
        // fourth quad
        else if (real>=0 && imaginary>=0) {
            thetaRadians = Math.acos(real/r);
            thetaRadians += Math.PI;
        }
        return thetaRadians;
    }

    public String print() {
        String output = "";
        output += real;
        output += "+(";
        output += imaginary;
        output += ")i";
        return output;
    }
}
