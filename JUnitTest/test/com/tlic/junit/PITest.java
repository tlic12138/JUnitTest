package com.tlic.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// 超时测试
public class PITest {
	
	PI pi;
	
	@Before
	public void setUp() throws Exception{
		pi = new PI();
	}

	// 设置最大处理时间500ms
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
	
	// 实测处理1亿次需要0.515s，即515ms，超出设定时间，测试失败，可以改进算法或者调高超时时间
	@Test(timeout = 500)
	public void test100m() {
		double r = pi.calculate(100000000);
		assertEquals(3.14159, r, 0.00001);
	}

}
