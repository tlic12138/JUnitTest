package com.tlic.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

	// 声明成员变量
	Calculator calc;
	
	// 加@Before注解，给成员变量赋值，做了这个设置后，当前测试类中所有的测试方法都能用这个成员变量
	@Before
	public void setUp() {
		calc = new Calculator();
	}
	
	@Test
	public void testCalculate() {
		assertEquals(3, calc.calculate("1+2"));
		assertEquals(6, calc.calculate("1+2+3"));
		assertEquals(35, calc.calculate("12+23"));
	}
	
	@Test
	public void testCalculateWithSpace() {
		assertEquals(3, calc.calculate("1 + 2"));
	}
	
	@Test
	public void testCalcAddLargeNumers() {
		int r = calc.calculate("123 + 456");
		assertEquals(579, r);
	}
	
	@Test
	public void testCalcWithWhiteSpaces() {
		int r = calc.calculate("1+5 + 10");
		assertEquals(16, r);
	}
	
	// 针对异常情况的测试，括号中的内容代表：如果发生NumberFormatException异常，就算测试通过
	@Test(expected = NumberFormatException.class)
	public void testCalcWithEmptyString() {
		calc.calculate("");
	}
	
	// 第一次测试时没报NumberFormatException异常，因为字符串为空却调用了aplit()方法先报了NullPointerException，
	// 所以要先加判断不为null的处理
	@Test(expected = NumberFormatException.class)
	public void testCalcWithNullString() {
		calc.calculate(null);
	}

}
