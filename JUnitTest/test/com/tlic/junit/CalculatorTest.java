package com.tlic.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

	// ������Ա����
	Calculator calc;
	
	// ��@Beforeע�⣬����Ա������ֵ������������ú󣬵�ǰ�����������еĲ��Է��������������Ա����
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
	
	// ����쳣����Ĳ��ԣ������е����ݴ����������NumberFormatException�쳣���������ͨ��
	@Test(expected = NumberFormatException.class)
	public void testCalcWithEmptyString() {
		calc.calculate("");
	}
	
	// ��һ�β���ʱû��NumberFormatException�쳣����Ϊ�ַ���Ϊ��ȴ������aplit()�����ȱ���NullPointerException��
	// ����Ҫ�ȼ��жϲ�Ϊnull�Ĵ���
	@Test(expected = NumberFormatException.class)
	public void testCalcWithNullString() {
		calc.calculate(null);
	}

}
