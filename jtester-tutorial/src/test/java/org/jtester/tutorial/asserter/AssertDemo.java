package org.jtester.tutorial.asserter;

import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.core.StringContains;
import org.jtester.testng.JTester;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AssertDemo extends JTester {
	private String statement = "this is 'abc' text";

	/**
	 * �Ƚ�3�ֲ�ͬ���Ķ������
	 */
	public void compareAssertStyle() {
		// junit\testng��ʽ
		Assert.assertTrue(statement.contains("abc"));

		// hamcrest��ʽ
		assertThat(statement, StringContains.containsString("abc"));

		// jtester��ʽ
		want.string(statement).contains("abc");
	}

	/**
	 * ���ӵ���֤����
	 */
	public void testComplexStyle() {

	}
}
