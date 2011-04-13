package org.jtester.tutorial01.debugit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JUnitDemo {
	@BeforeClass
	public static void method1() {
		System.out.println("before class");
	}

	@AfterClass
	public static void method2() {
		System.out.println("after class");
	}

	@Before
	public void method3() {
		System.out.println("\tbefore method");
	}

	@After
	public void method4() {
		System.out.println("\tafter method");
	}

	@Test
	public void test1() {
		System.out.println("\t\ttest1 method");
	}

	@Test
	public void test2() {
		System.out.println("\t\ttest2 method");
	}
}
