package controller;

public class Complex {
	
	private final double re;
	private final double im;
	
	public Complex(double real, double imag) {
        re = real;
        im = imag;
    }
	
	public String toString() {
		if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }
	
	public double abs() {
        return Math.hypot(re, im);
    }
	
	public Complex plus(Complex b) {
		Complex a = this;
        double real = a.re + b.re;
        double imag = a.im + b.im;
        return new Complex(real, imag);
    }
	
	public Complex minus(Complex b) {
		Complex a = this;
	    double real = a.re - b.re;
	    double imag = a.im - b.im;
	    return new Complex(real, imag);
	}
	
	public Complex times(Complex b) {
		Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }
	
	public boolean isGreater(Complex b) {
		Complex a = this;
		if (a.abs() > b.abs()) return true;
		return false;
	}
	
	public static Complex power(Complex a, int k) {
		Complex result = a;
		if (k == 0) return new Complex(1.00, 0.00);
		if (k == 1) return a;
		for (int i = 0; i < k - 1; i++) {
			result = result.times(a);
		}
		return result;
	}
	
	public double re() { 
		return re; 
	}
	
    public double im() { 
    	return im; 
    }

}
