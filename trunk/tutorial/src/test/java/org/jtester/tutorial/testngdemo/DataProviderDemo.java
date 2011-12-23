package org.jtester.tutorial.testngdemo;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("rawtypes")
@Test
public class DataProviderDemo extends JTester {
	@Test(dataProvider = "range-provider")
	public void testIsBetween(int value, int lower, int upper, boolean expected) {
		boolean actual = this.between(value, lower, upper);

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

	/**
	 * 假定区间是 [1,8]<br>
	 * 我们输入值为0<br>
	 * between返回值应该是false
	 */
	@Test
	public void testBetween_NumberLessThanMin_ShouldReturnFalse() {
		boolean result = between(0, 1, 8);
		assert result == false;
	}

	/**
	 * 假定区间是 [1,8]<br>
	 * 我们输入不同的边界值<br>
	 * between返回值应该要符合我们的期望值expected
	 */
	@Test(dataProvider = "dataForBetween")
	public void testBetween2(int input, boolean expected) {
		boolean result = between(0, 1, 8);
		assert result == false;
	}

	@DataProvider
	public Object[][] dataForBetween() {
		return new Object[][] {// <br>
		/** 当输入值是0时，返回值应该是false **/
		new Object[] { 0, false },
		/** 当输入值是1时，返回值应该是true **/
		new Object[] { 1, true },
		/** 当输入值是5时，返回值应该是true **/
		new Object[] { 5, true },
		/** 当输入值是8时，返回值应该是true **/
		new Object[] { 8, true },
		/** 当输入值是9时，返回值应该是false **/
		new Object[] { 9, false } };
	}

	@DataProvider
	public Iterator dataForBetween2() {
		return new DataIterator() {
			{
				/** 当输入值是0时，返回值应该是false **/
				data(0, false);
				/** 当输入值是1时，返回值应该是true **/
				data(1, true);
				/** 当输入值是5时，返回值应该是true **/
				data(5, true);
				/** 当输入值是8时，返回值应该是true **/
				data(8, true);
				/** 当输入值是9时，返回值应该是false **/
				data(9, false);
			}
		};
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testBetween_RangeIsError() {
		between2(5, 8, 1);
	}

	@Test
	public void testBetween_RangeIsError2() {
		try {
			between2(5, 8, 1);
			assert false;
		} catch (Exception e) {
			String message = e.getMessage();
			assert message == "the number min must less than the number max.";
		}
	}

	private boolean between(int input, int min, int max) {
		return input >= min && input <= max;
	}

	private boolean between2(int input, int min, int max) {
		if (min > max) {
			throw new RuntimeException("the number min must less than the number max.");
		}
		return input >= min && input <= max;
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
