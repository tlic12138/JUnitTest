package com.tlic.junit;

import org.junit.Before;
import org.junit.Test;

import com.tlic.util.Calcuate;

// ��Assert�е�static����ȫ���������
import static org.junit.Assert.*;

// ��̬����hamcrest.Matchers
import static org.hamcrest.Matchers.*;

import org.junit.Assert;

public class TestCalcuate {
	
	Calcuate cal;

	// ִ������һ������֮ǰ����ִ��setUp����
	@Before 
	public void setUp() {
		cal = new Calcuate();
	}
	
	// ����@Test��ʾ�÷�����һ����Ԫ���Է���
	@Test
	public void testAdd() {
		int retValue = cal.add(12,22);
		/**
		 * ���¾���һ���򵥵Ķ��Եı�д
		 * ��һ�������ǣ���������������ʾ��Ϣ
		 * �ڶ��������ǣ�����ִ�����֮��ʵ��ֵ
		 * �����������ǣ�����ִ�к��Ԥ��ֵ
		 */
		Assert.assertEquals("�ӷ�������", retValue, 34);
	}
	
	@Test
	public void testMinus() {
		int retValue = cal.minus(15, 10);
		// �������˾�̬����֮��Assert�е����о�̬�����Ͳ������������������
		assertEquals("����������",retValue, 5);
	}
	
	@Test
	public void testMul() {
		int retValue = cal.mul(10, 5);
		assertEquals("�˷�������",retValue, 50);
	}
	
	@Test
	public void testDiv01() {
		int retValue = cal.divide(20, 4);
		assertEquals("����������",retValue, 5);
	}
	
	// expected���������쳣������������У��������ArithmeticException�쳣��ͨ��
	@Test(expected = ArithmeticException.class)
	public void testDiv02() {
		int retValue = cal.divide(20, 0);
		assertEquals("����������",retValue, 5);
	}
	
	// �򵥵����ܲ��ԣ�timeout�޶�����ʱ�䣬��ʾ�������Ӧ��300�����ڽ�������ȷ
	@Test(timeout = 300)
	public void testTime() throws InterruptedException {
		// ����300�������쳣�������ͨ��
		Thread.sleep(200);
	}
	
	@Test
	public void testHamcrest() {
		
		// ������Ҫ��̬����import static org.hamcrest.Matchers.*;
		
		// 22�Ƿ����20��С��40
		assertThat(22, allOf(greaterThan(20),lessThan(40)));
		
		// �ж��ַ����Ƿ����ַ���txt��β
		assertThat("abc.txt", endsWith("txt"));
	}
}
