package org.jtester.hamcrest.iassert.common.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class BaseAssertTest extends JTester {

	@Test(expectedExceptions = { AssertionError.class })
	public void testClazIs() {
		want.map(new HashMap<String, Object>()).clazIs(String.class);
	}

	public void testClazIs2() {
		want.map(new HashMap<String, Object>()).clazIs(Map.class);
	}

	@Test
	public void testAllOf() {
		want.string("test1").allOf(the.string().contains("test"), the.string().regular("\\w{5}"));
	}

	@Test
	public void testAllOf_iterable() {
		want.string("test1").allOf(Arrays.asList(the.string().contains("test"), the.string().regular("\\w{5}")));
	}

	@Test
	public void testAnyOf() {
		want.string("test1").anyOf(the.string().contains("test4"), the.string().regular("\\w{5}"));
		want.string("test1").anyOf(the.string().contains("test1"), the.string().regular("\\w{6}"));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testAnyOf_failure() {
		want.string("test1").anyOf(the.string().contains("test4"), the.string().regular("\\w{6}"));
	}

	@Test
	public void testAnyOf_iterable() {
		want.string("test1").anyOf(Arrays.asList(the.string().contains("test"), the.string().regular("\\w{6}")));
		want.string("test1").anyOf(Arrays.asList(the.string().contains("test5"), the.string().regular("\\w{5}")));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testAnyOf_iterable_failure() {
		want.string("test1").anyOf(Arrays.asList(the.string().contains("test6"), the.string().regular("\\w{6}")));
	}
}
