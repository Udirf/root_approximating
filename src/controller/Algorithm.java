package controller;

public class Algorithm {
	
	public static String[] a;

	public static boolean check(String poly, String k) { // method for checking if the input values are valid

		if (poly.length() > 0 && k.length() > 0)
			return true;
		return false;

	}
	
	public static Integer findDegree(String poly) {
		if (poly.matches("(.*)z(?!\\^)(.*)")) poly = poly.substring(0, poly.lastIndexOf("z") + 1) + "^1" + poly.substring(poly.lastIndexOf("z") + 1, poly.length()); //adding z^1
		
		poly = poly.replaceAll("\\s", ""); // removing whitespace characters
		
		int i = 1;
		while (i < poly.length()) {
			if (poly.charAt(i) == '-' && poly.charAt(i-1) != '(') poly = poly.substring(0, i) + "+" + poly.substring(i++);
			i++;
		}
		
		a = poly.split("z"); // splitting the polynomial
		return Integer.parseInt(a[1].substring(a[1].indexOf("^") + 1, a[1].indexOf("+")));
	}
	
	public static void findCoefficients(Integer n, String[] co, Complex[] coefficient) { //method for finding the coefficients of the polynomial
		
		coefficient[0] = new Complex(1.00, 0.00); // 1st coefficient is always 1
		Integer x = 1;
		Integer d;
		
		for (int i = 1; i <= n; i++) {
			if(x == a.length - 1 && a[x].matches("\\^(\\d+)$")) break; // if there is no coefficient for x^0, break
			if(x == a.length - 1) d = 0; // for the last part of the polynomial the degree is 0
			else if(a[x + 1].indexOf("+") == -1)  d = Integer.parseInt(a[x + 1].substring(a[x + 1].indexOf("^") + 1, a[x + 1].length()));
			else d = Integer.parseInt(a[x + 1].substring(a[x + 1].indexOf("^") + 1, a[x + 1].indexOf("+"))); // finding out the degree
			if(d != (n-i)) {
				coefficient[i] = new Complex(0.00, 0.00);
				continue; // if the degree does not equal to the next coefficient, skip
			}
			co[i] = a[x].substring(a[x].indexOf("+") + 1, a[x].length()); // finding out the coefficients of the polynomial
			if(co[i].contains("i")) { // if there is imaginary part...
				if(co[i].indexOf("+") == -1) { // if there is no real part...
					if(co[i].indexOf("(") + 1 == co[i].indexOf("i")) coefficient[i] = new Complex(0.00, 1.00);
					else if(co[i].charAt(co[i].indexOf("i") - 1) == '-') coefficient[i] = new Complex(0.00, -1.00);
					else coefficient[i] = new Complex(0.00, Double.valueOf(co[i].substring(co[i].indexOf("(") + 1, co[i].indexOf("i"))));
				}
				else {
					if(co[i].indexOf("+") + 1 == co[i].indexOf("i")) coefficient[i] = new Complex(Double.valueOf(co[i].substring(co[i].indexOf("(") + 1, co[i].indexOf("+"))), 1.00);
					else if(co[i].charAt(co[i].indexOf("i") - 1) == '-') coefficient[i] = new Complex(Double.valueOf(co[i].substring(co[i].indexOf("(") + 1, co[i].indexOf("+"))), -1.00);
					else coefficient[i] = new Complex(Double.valueOf(co[i].substring(co[i].indexOf("(") + 1, co[i].indexOf("+"))), Double.valueOf(co[i].substring(co[i].indexOf("+") + 1, co[i].indexOf("i"))));
				}
			}
			else {
				if(co[i].length() == 0) coefficient[i] = new Complex(1.00, 0.00);
				else coefficient[i] = new Complex(Double.valueOf(co[i]), 0.00);
			}
			x++;
		}
		
	}
	
	public static Double findB0(Integer n, Complex[] coefficient) {
		
		Double m;
		Double max = coefficient[1].abs();
		
		for (int i = 2; i <= n; i++) {
			m = Math.pow(coefficient[i].abs(), (1.00 / (double) i));
			if(m > max) max = m;
		}
		
		return 2 * max;
		
	}
	
	public static Double findM(Double b, Integer k) {
		
		return Math.log10(b * Math.pow(2, k));
		
	}
	
	public static int partition(Complex[] min, Complex[] est, int low, int high) 
    { 
        Complex pivot = est[high];  
        int i = (low-1); // index of smaller element 
        for (int j=low; j<high; j++) 
        { 
            if (pivot.isGreater(est[j])) 
            { 
                i++; 
                Complex temp_min = min[i]; 
                min[i] = min[j]; 
                min[j] = temp_min; 
                Complex temp_est = est[i];
                est[i] = est[j];
                est[j] = temp_est;
            } 
        } 
        Complex temp_min = min[i+1]; 
        min[i+1] = min[high]; 
        min[high] = temp_min; 
        Complex temp_est = est[i+1];
        est[i+1] = est[high];
        est[high] = temp_est;
        return i+1; 
    } 

	public static void sort(Complex[] min, Complex[] est, int low, int high) { 
        if (low < high) { 
            int pi = partition(min, est, low, high); 
            sort(min, est, low, pi-1); 
            sort(min, est, pi+1, high); 
        } 
	} 
	
	public static Solution[] findOneRoot(Integer n, Complex[] coefficient, Double b, Double m, Complex z, Integer k) {
		
		Complex c = new Complex(0.00, 0.00);
		Complex estimate = new Complex(0.00, 0.00);
		Complex minimum = new Complex(0.00, 0.00);
		Complex minimumEstimate = new Complex(0.00, 0.00);
		Complex[] roots = new Complex[1];
		Solution[] solutions = new Solution[1];
		Integer grid = 8 * n * n;
		Complex[] minInit = new Complex[grid * grid];
		Complex[] minEstimateInit = new Complex[grid * grid];
		int s = 0;
		int h = 0;
		int l = 0;
		boolean ok = true;
		int m_circles = (int) (Math.floor(m) + 1);
		Double bi = b;
		Double gridSize = b / ((double) grid / 2);
		roots[0] = new Complex(0.00, 0.00);
		for (int x = 0; x < grid; x++) {
			for (int y = 0; y < grid; y++) {
				c = new Complex(z.re() - b + (gridSize / 2) + (x * gridSize), z.im() + b - (gridSize / 2) - (y * gridSize));
				if(new Complex(c.re() - z.re(), c.im() - z.im()).abs() > b) continue;
				estimate = new Complex(0.00, 0.00);
				for (int j = 0; j <= n; j++) {
					estimate = estimate.plus(coefficient[j].times(Complex.power(c, n - j)));
				}
				minInit[h] = c;
				minEstimateInit[h++] = estimate;
			}
		}
		Complex[] min = new Complex[h];
		Complex[] minEstimate = new Complex[h];
		Complex[][] circleCenters = new Complex[h][];
		Double[][] circleRadius = new Double[h][];
		for (l = 0; l < h; l++) {
			min[l] = minInit[l];
			minEstimate[l] = minEstimateInit[l];
			circleCenters[l] = new Complex[m_circles];
			circleRadius[l] = new Double[m_circles];
			circleCenters[l][0] = new Complex(0.00, 0.00);
			circleRadius[l][0] = b;
		}
		sort(min, minEstimate, 0, h - 1);
		h = 0;
		l = 0;
		while (h < 1) {
			b = bi / 2;
			z = min[l];
			ok = true;
			for (int i = 1; i <= m; i++) {
				s = 0;
				circleCenters[h][i] = z;
				circleRadius[h][i] = b;
				for (int x = 0; x < grid; x++) {
					for (int y = 0; y < grid; y++) {
						c = new Complex(z.re() - b + (gridSize / 2) + (x * gridSize), z.im() + b - (gridSize / 2) - (y * gridSize));
						if(new Complex(c.re() - z.re(), c.im() - z.im()).abs() > b) continue;
						s++;
						estimate = new Complex(0.00, 0.00);
						for (int j = 0; j <= n; j++) {
							estimate = estimate.plus(coefficient[j].times(Complex.power(c, n - j)));
						}
						if(s == 1)  {
							minimum = c;
							minimumEstimate = estimate;
						}
						if(minimumEstimate.isGreater(estimate)) {
							minimum = c;
							minimumEstimate = estimate;
						}
					}
				}
				b /= 2;
				z = minimum;
			}
			//if(minimumEstimate.isGreater(minEstimate[h])) break;
			for(int w = 0; w < h; w++) if((roots[w].minus(minimum)).abs() < Math.pow(2, -k)) {
				l++;
				ok = false;
			}
			if(ok == true) {
				l++;
				solutions[h] = new Solution(minimum, m_circles, circleCenters[h], circleRadius[h]);
				roots[h++] = minimum;
			}
		}
		
		return solutions;
		
	}
	
	public static Solution[] findRoot(Integer n, Complex[] coefficient, Double b, Double m, Complex z, Integer k) {
		
		Complex c = new Complex(0.00, 0.00);
		Complex estimate = new Complex(0.00, 0.00);
		Complex minimum = new Complex(0.00, 0.00);
		Complex minimumEstimate = new Complex(0.00, 0.00);
		Complex[] roots = new Complex[n];
		Solution[] solutions = new Solution[n];
		Integer grid = 8 * n * n;
		Complex[] minInit = new Complex[grid * grid];
		Complex[] minEstimateInit = new Complex[grid * grid];
		int s = 0;
		int h = 0;
		int l = 0;
		boolean ok = true;
		int m_circles = (int) (Math.floor(m) + 1);
		Double bi = b;
		Double gridSize = b / ((double) grid / 2);
		for (int i = 0; i < n; i++) roots[i] = new Complex(0.00, 0.00);
		for (int x = 0; x < grid; x++) {
			for (int y = 0; y < grid; y++) {
				c = new Complex(z.re() - b + (gridSize / 2) + (x * gridSize), z.im() + b - (gridSize / 2) - (y * gridSize));
				if(new Complex(c.re() - z.re(), c.im() - z.im()).abs() > b) continue;
				estimate = new Complex(0.00, 0.00);
				for (int j = 0; j <= n; j++) {
					estimate = estimate.plus(coefficient[j].times(Complex.power(c, n - j)));
				}
				minInit[h] = c;
				minEstimateInit[h++] = estimate;
			}
		}
		Complex[] min = new Complex[h];
		Complex[] minEstimate = new Complex[h];
		Complex[][] circleCenters = new Complex[h][];
		Double[][] circleRadius = new Double[h][];
		for (l = 0; l < h; l++) {
			min[l] = minInit[l];
			minEstimate[l] = minEstimateInit[l];
			circleCenters[l] = new Complex[m_circles];
			circleRadius[l] = new Double[m_circles];
			circleCenters[l][0] = new Complex(0.00, 0.00);
			circleRadius[l][0] = b;
		}
		sort(min, minEstimate, 0, h - 1);
		h = 0;
		l = 0;
		while (h < n) {
			b = bi / 2;
			z = min[l];
			ok = true;
			for (int i = 1; i <= m; i++) {
				s = 0;
				circleCenters[h][i] = z;
				circleRadius[h][i] = b;
				for (int x = 0; x < grid; x++) {
					for (int y = 0; y < grid; y++) {
						c = new Complex(z.re() - b + (gridSize / 2) + (x * gridSize), z.im() + b - (gridSize / 2) - (y * gridSize));
						if(new Complex(c.re() - z.re(), c.im() - z.im()).abs() > b) continue;
						s++;
						estimate = new Complex(0.00, 0.00);
						for (int j = 0; j <= n; j++) {
							estimate = estimate.plus(coefficient[j].times(Complex.power(c, n - j)));
						}
						if(s == 1)  {
							minimum = c;
							minimumEstimate = estimate;
						}
						if(minimumEstimate.isGreater(estimate)) {
							minimum = c;
							minimumEstimate = estimate;
						}
					}
				}
				b /= 2;
				z = minimum;
			}
			//if(minimumEstimate.isGreater(minEstimate[h])) break;
			for(int w = 0; w < h; w++) if((roots[w].minus(minimum)).abs() < Math.pow(2, -k)) {
				l++;
				ok = false;
			}
			if(ok == true) {
				l++;
				solutions[h] = new Solution(minimum, m_circles, circleCenters[h], circleRadius[h]);
				roots[h++] = minimum;
			}
		}
		
		return solutions;
		
	}

	public static Solution[] initOneSolution(String poly, Integer n, Integer k) { // initializing variables for the main algorithm
		
		Complex[] coefficient = new Complex[n+1];
		String[] co = new String[n+1];
		
		findCoefficients(n, co, coefficient);
		
		Double b = findB0(n, coefficient);
		
		Double m = findM(b, k);
		
		Complex z = new Complex(0.00, 0.00);
		
		Solution[] c = findOneRoot(n, coefficient, b, m, z, k);
		
		return c;
		
	}
	
	public static Solution[] init(String poly, Integer n, Integer k) { // initializing variables for the main algorithm
		
		Complex[] coefficient = new Complex[n+1];
		String[] co = new String[n+1];
		
		findCoefficients(n, co, coefficient);
		
		Double b = findB0(n, coefficient);
		
		Double m = findM(b, k);
		
		Complex z = new Complex(0.00, 0.00);
		
		Solution[] c = findRoot(n, coefficient, b, m, z, k);
		
		return c;
		
	}

}
