public class Complex {
    private final double re;   // real number
    private final double im;   // imaginary number

    protected Complex(double real, double imag) { // object for storing real + imaginary number in one spot
        re = real;
        im = imag;
    }

    public String toString() {
        return String.format("%.6f", re) + " " + String.format("%.6f", im); // formatting per assignment specifications
    }

    protected Complex plus(Complex b) { // return a complex plus another complex
        Complex a = this;             // this.times(b) invocation
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }

    protected Complex minus(Complex b) { // return a complex minus another complex
        Complex a = this;
        double real = a.re - b.re;
        double imag = a.im - b.im;
        return new Complex(real, imag);
    }

    protected Complex times(Complex b) { // return a complex times another complex
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }
}