package com.tlic.junit;

import org.junit.Before;
import org.junit.Test;

import com.tlic.util.Calcuate;

// 把Assert中的static方法全部导入进来
import static org.junit.Assert.*;

// 静态导入hamcrest.Matchers
import static org.hamcrest.Matchers.*;

import org.junit.Assert;

public class TestCalcuate {
	
	Calcuate cal;

	// 执行任意一个方法之前都会执行setUp方法
	@Before 
	public void setUp() {
		cal = new Calcuate();
	}
	
	// 加了@Test表示该方法是一个单元测试方法
	@Test
	public void testAdd() {
		int retValue = cal.add(12,22);
		/**
		 * 以下就是一个简单的断言的编写
		 * 第一个参数是：如果出错给出的提示信息
		 * 第二个参数是：方法执行完成之后实际值
		 * 第三个参数是：方法执行后的预期值
		 */
		Assert.assertEquals("加法有问题", retValue, 34);
	}
	
	@Test
	public void testMinus() {
		int retValue = cal.minus(15, 10);
		// 当进行了静态导入之后，Assert中的所有静态方法就不用再添加类名调用了
		assertEquals("减法有问题",retValue, 5);
	}
	
	@Test
	public void testMul() {
		int retValue = cal.mul(10, 5);
		assertEquals("乘法有问题",retValue, 50);
	}
	
	@Test
	public void testDiv01() {
		int retValue = cal.divide(20, 4);
		assertEquals("除法有问题",retValue, 5);
	}
	
	// expected用来捕获异常，这个测试类中，如果发生ArithmeticException异常就通过
	@Test(expected = ArithmeticException.class)
	public void testDiv02() {
		int retValue = cal.divide(20, 0);
		assertEquals("除法有问题",retValue, 5);
	}
	
	// 简单的性能测试，timeout限定运行时间，表示这个方法应在300毫秒内结束才正确
	@Test(timeout = 300)
	public void testTime() throws InterruptedException {
		// 超过300毫秒则报异常，否则就通过
		Thread.sleep(200);
	}
	
	@Test
	public void testHamcrest() {
		
		// 首先需要静态导入import static org.hamcrest.Matchers.*;
		
		// 22是否大于20并小于40
		assertThat(22, allOf(greaterThan(20),lessThan(40)));
		
		// 判断字符串是否以字符串txt结尾
		assertThat("abc.txt", endsWith("txt"));
	}
}
