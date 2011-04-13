package org.jtester.tutorial01.debugit.testng;

import java.lang.reflect.Method;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class DataProviderDemo extends JTester {
	@Test(dataProvider = "range-provider")
	public void testIsBetween(int value, int lower, int upper, boolean expected) {
		boolean actual = this.between(lower, upper, value);

		want.bool(actual).is(expected);
	}

	@DataProvider(name = "range-provider")
	public Object[][] rangeData() {
		return new Object[][] { /** <br> */
		{ 4, 5, 10, false }, /** <br> */
		{ 5, 5, 10, true }, /** <br> */
		{ 6, 5, 10, true }, /** <br> */
		{ 10, 5, 10, true }, /** <br> */
		{ 11, 5, 10, false }, };
	}

	private boolean between(int lower, int upper, int value) {
		return value >= lower && value <= upper;
	}

	/**
	 * DataProvider还可以根据方法Method以及上下文ITestContext提供数据
	 * 
	 * @param method
	 * @return
	 */
	@DataProvider
	public Object[][] provideNumbers(Method method) {
		Object[][] result = null;
		if (method.getName().equals("two")) {
			result = new Object[][] { { 2 }, { 4 } };
		} else if (method.getName().equals("three")) {
			result = new Object[][] { { 3 }, { 6 } };
		}
		return result;
	}

	@Test(dataProvider = "provideNumbers")
	public void two(int param) {
		System.out.println("Two received: " + param);
	}

	@Test(dataProvider = "provideNumbers")
	public void three(int param) {
		System.out.println("Three received: " + param);
	}
}
