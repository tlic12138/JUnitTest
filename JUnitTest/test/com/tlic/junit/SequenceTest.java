package com.tlic.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SequenceTest {

	// 1
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass()");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass()");
	}
	
	// 3
	@Before
	public void setUp() throws Exception{
		System.out.println("    setUp()");
	}
	
	// 5
	@After
	public void tearDown() throws Exception{
		System.out.println("    tearDown()");
	}
	
	// 2
	public SequenceTest() {
		System.out.println("  new SequenceTest()");
	}
	
	// 4
	@Test
	public void testA() {
		System.out.println("    testA()");
	}
	
	@Test
	public void testB() {
		System.out.println("    testB()");
	}
	
	@Test
	public void testC() {
		System.out.println("    testC()");
	}
}
