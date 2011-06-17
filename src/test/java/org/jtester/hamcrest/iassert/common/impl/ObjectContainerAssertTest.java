package org.jtester.hamcrest.iassert.common.impl;

import java.util.Arrays;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ObjectContainerAssertTest extends JTester {

	@Test
	public void testHasItems_objects() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems("test1", "test2");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasItems_objects_failure() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems("test1", "test4");
	}

	@Test
	public void testHasItems_array() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems(new String[] { "test1", "test2" });
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasItems_array_failure() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems(new String[] { "test1", "test4" });
	}

	@Test
	public void testHasItems_collection() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems(Arrays.asList("test1", "test2"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasItems_collection_failure() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItems(Arrays.asList("test1", "test4"));
	}

	@Test
	public void testAllItemMatch_regex() {
		want.collection(Arrays.asList("test1", "test2", "test3")).allItemMatch("\\w{5}", "test[\\d]");
	}

	@Test
	public void testHasItemMatch() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItemMatch("test1", "test2");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasItemMatch_failure() {
		want.collection(Arrays.asList("test1", "test2", "test3")).hasItemMatch("test1", "test4");
	}

	@Test
	public void testAllItemMatch_And_Matcher() {
		want.array(new String[] { "ab345c", "ab345cd" }).allItemMatch(the.string().regular("\\w+\\d+\\w+"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testAllItemMatch_And_Matcher_Failure() {
		want.array(new String[] { "ab345c", "abcd" }).allItemMatch(the.string().regular("\\w+\\d+\\w+"));
	}

	@Test
	public void testHasItemMatch_OR_Matcher() {
		want.array(new String[] { "abc", "ab345cd" }).hasItemMatch(the.string().regular("\\w+\\d+\\w+"),
				the.string().isEqualTo("abc"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testHasItemMatch_OR_Matcher_Failure() {
		want.array(new String[] { "ddd", "abcd" }).allItemMatch(the.string().regular("\\w+\\d+\\w+"));
	}
}
