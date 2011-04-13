package org.jtester.tutorial01.asserter;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.StringContains;
import org.jtester.testng.JTester;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AssertDemo extends JTester {
	private String statement = "this is 'abc' text";

	/**
	 * 比较3种不同风格的断言语句
	 */
	public void compareAssertStyle() {
		// junit\testng方式
		Assert.assertTrue(statement.contains("abc"));

		// hamcrest方式
		assertThat(statement, StringContains.containsString("abc"));

		// jtester方式
		want.string(statement).contains("abc");
	}

	/**
	 * 复杂的验证功能
	 */
	public void testComplexStyle() {

	}
}
