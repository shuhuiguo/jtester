package org.jtester.hamcrest.iassert.object.impl;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class StringAssertTest extends JTester {

	@Test
	public void testNotContain() {
		String tested = "i am tested string";
		want.string(tested).notContain("is");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testNotContain_fail() {
		String tested = "i am tested string";
		want.string(tested).contains("tested");
		want.string(tested).notContain("tested");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testNotContain_fail2() {
		String tested = "i am tested string";
		want.string(tested).not(the.string().contains("tested"));
	}

	@Test
	public void testNotBlank() {
		String tested = "i am tested string";
		want.string(tested).notBlank();
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testNotBlank_fail() {
		String tested = "	\t \n";
		want.string(tested).notBlank();
	}
}
