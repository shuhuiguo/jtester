package org.jtester.hamcrest;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class TestNumberAssert extends JTester {
	@Test
	public void test1() {
		want.number(3).between(2, 5);
		want.number(3).greaterEqual(3);
		want.number(3).greaterThan(2);
		want.number(3).lessEqual(3);
		want.number(3).lessThan(4);
		want.number(3).isEqualTo(3);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void test2() {
		want.number(3).between(5, 2);
	}

	public void test3() {
		want.number(5d).greaterEqual(4d);
	}

	// /**
	// * the.wanted(Class<T> claz)的类型转换
	// */
	// @SuppressWarnings( { "unchecked", "unused" })
	// @Test(expectedExceptions = JTesterException.class)
	// public void testTheNumber() {
	// Long l = the.doublenum().isEqualTo(3d).wanted(Long.class);
	// Double db = (Double) the.number().isEqualTo(3d).wanted(Double.class);
	// }
}
