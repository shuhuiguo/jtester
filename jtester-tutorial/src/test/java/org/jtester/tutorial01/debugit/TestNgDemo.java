package org.jtester.tutorial01.debugit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNgDemo {
	@BeforeClass
	public void method1() {
		System.out.println("before class");
	}

	@AfterClass
	public void method2() {
		System.out.println("after class");
	}

	@BeforeMethod
	public void method3() {
		System.out.println("\tbefore method");
	}

	@AfterMethod
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
