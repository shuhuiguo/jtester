package org.jtester.hamcrest.iassert.common.impl;

import java.util.Arrays;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class ListHasItemsAssertTest extends JTester {

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
	public void testItemsAllMatch_regex() {
		want.collection(Arrays.asList("test1", "test2", "test3")).matchAllItems("\\w{5}", "test[\\d]");
	}

	@Test
	public void testMatchAnyItem() {
		want.collection(Arrays.asList("test1", "test2", "test3")).matchAnyItems("test1", "test2");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatchAnyItem_failure() {
		want.collection(Arrays.asList("test1", "test2", "test3")).matchAnyItems("test1", "test4");
	}

	@Test
	public void testItemsAllMatch_And_Matcher() {
		want.array(new String[] { "ab345c", "ab345cd" }).matchAllItems(the.string().regular("\\w+\\d+\\w+"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testItemsAllMatch_And_Matcher_Failure() {
		want.array(new String[] { "ab345c", "abcd" }).matchAllItems(the.string().regular("\\w+\\d+\\w+"));
	}

	@Test
	public void testMatchAnyItem_OR_Matcher() {
		want.array(new String[] { "abc", "ab345cd" }).matchAnyItems(the.string().regular("\\w+\\d+\\w+"),
				the.string().isEqualTo("abc"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatchAnyItem_OR_Matcher_Failure() {
		want.array(new String[] { "ddd", "abcd" }).matchAnyItems(the.string().regular("\\w+\\d+\\w+"));
	}
}
