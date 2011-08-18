package org.jtester.hamcrest.iassert.object.impl;

import org.jtester.hamcrest.matcher.string.StringMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
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

	@Test
	public void testEqIgnorBlank() {
		want.string("i 	am  string").eqWithStripSpace("i  am string");
	}

	@Test
	public void testEqIgnoreSpace() {
		want.string("i am a string").eqIgnoreSpace("iama string");
	}

	@Test
	public void testContains() {
		want.string("===A\tb\nC====").contains("a b c", StringMode.IgnoreCase, StringMode.SameAsSpace);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testContains_failure() {
		want.string("===A\tb\nC====").contains("a b c", StringMode.IgnoreCase);
	}

	@Test
	public void testEnd() {
		want.string("=====a b C").end("abc", StringMode.IgnoreCase, StringMode.IgnoreSpace);
	}

	@Test
	public void testStart() {
		want.string("a b C=====").start("abc", StringMode.IgnoreCase, StringMode.IgnoreSpace);
	}

	@Test
	public void testEqIgnoreCase() {
		want.string("Abc").eqIgnoreCase("aBc");
	}

	@Test
	public void testIsEqualTo() {
		want.string("abc").isEqualTo("abc");
		want.string("aBc").isEqualTo("Abc", StringMode.IgnoreCase);
	}

	@Test
	public void testContainsInOrder() {
		want.string("abc cde 123, 456").containsInOrder("abc", "123", "456");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testContainsInOrder_Failure() {
		want.string("abc cde 123, 456").containsInOrder("abc", "456", "123");
	}
}
