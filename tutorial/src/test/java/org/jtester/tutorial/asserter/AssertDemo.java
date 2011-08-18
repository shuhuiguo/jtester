package org.jtester.tutorial.asserter;

import static ext.jtester.hamcrest.MatcherAssert.assertThat;

import org.jtester.hamcrest.matcher.string.StringContainMatcher;
import org.jtester.testng.JTester;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AssertDemo extends JTester {
	private String statement = "this is 'abc' text";

	/**
	 * 三种模式的断言比较
	 */
	public void compareAssertStyle() {
		// junit\testng模式
		Assert.assertTrue(statement.contains("abc"));

		// hamcrest模式
		assertThat(statement, StringContainMatcher.contains("abc"));

		// jtester模式
		want.string(statement).contains("abc");
	}
}
