// NoahTheCorgi
// discrete modeling of complex field and its arithmetics::

public class Complex {

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
        // placeholder
    }
}
