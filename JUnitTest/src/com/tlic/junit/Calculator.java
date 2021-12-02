package com.tlic.junit;

import java.util.Arrays;

public class Calculator {
	
	// 字符串转整数型
	public int calculate(String expression) {
		if(expression == null) {
			throw new NumberFormatException("Expression is null");
		}
		String[] ss = expression.split("\\+");
		System.out.println(expression + " => " + Arrays.toString(ss));
		int sum = 0;
		for(String s : ss) {
			sum = sum + Integer.parseInt(s.trim());
		}
		return sum;
	}
	
	public static void main(String[] args) {
		Calculator c = new Calculator();
		int r = c.calculate("1+2+3");
		System.out.println(r);
	}
	
}
