package com.tlic.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// ��ʱ����
public class PITest {
	
	PI pi;
	
	@Before
	public void setUp() throws Exception{
		pi = new PI();
	}

	// ���������ʱ��500ms
	@Test(timeout = 500)
	public void test1k() {
		double r = pi.calculate(1000);
		assertEquals(3.14, r, 0.01);
	}
	
	@Test(timeout = 500)
	public void test1m() {
		double r = pi.calculate(1000000);
		assertEquals(3.1416, r, 0.0001);
	}
	
	// ʵ�⴦��1�ڴ���Ҫ0.515s����515ms�������趨ʱ�䣬����ʧ�ܣ����ԸĽ��㷨���ߵ��߳�ʱʱ��
	@Test(timeout = 500)
	public void test100m() {
		double r = pi.calculate(100000000);
		assertEquals(3.14159, r, 0.00001);
	}

}
