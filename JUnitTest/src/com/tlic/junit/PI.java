package com.tlic.junit;

public class PI {

	// ¦Ð/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 - ...
	public double calculate(int count) {
		double sum = 0;
		boolean positive = true;
		int n = 0;
		for(int i = 1;;i += 2) {
			sum = sum + (positive ? 4.0 : -4.0) / i;
			positive = !positive;
			n++;
			if(n == count) {
				break;
			}
		}
		return sum;
	}
}
