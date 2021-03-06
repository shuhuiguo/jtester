package org.jtester.hamcrest.matcher.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ext.jtester.hamcrest.Matcher;
import ext.jtester.hamcrest.MatcherAssert;
import org.jtester.hamcrest.matcher.array.ListEveryItemMatcher.ItemMatcherType;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Test(groups = { "jtester", "assertion" })
public class ItemMatcherTest_AllItemMatcher extends JTester {
	Matcher m1 = the.string().contains("abc");

	ListEveryItemMatcher matcher = new ListEveryItemMatcher(m1, ItemMatcherType.AND);

	@Test
	public void testMatches_集合() {
		MatcherAssert.assertThat(Arrays.asList("ddd abc ddd", "ddddabcd"), matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatcher_正则表达式() {
		Matcher m2 = the.string().regular("\\w+\\d+\\w+");

		ListEveryItemMatcher arrayMatcher = new ListEveryItemMatcher(m2, ItemMatcherType.AND);
		MatcherAssert.assertThat(new String[] { "ab345c", "abcd" }, arrayMatcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_集合_Failure() {
		MatcherAssert.assertThat(Arrays.asList("ddd abc ddd", "ddddd"), matcher);
	}

	@Test
	public void testMatches_数组() {
		MatcherAssert.assertThat(new String[] { "ddd abc ddd", "ddabcddd" }, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_数组_Failure() {
		MatcherAssert.assertThat(new String[] { "ddd abc ddd", "ddddd" }, matcher);
	}

	@Test
	public void testMatches_Map() {
		Map map = new HashMap() {
			private static final long serialVersionUID = 1L;
			{
				put("key1", "ddd abc ddd");
				put("key2", "ddabcdd");
			}
		};
		MatcherAssert.assertThat(map, matcher);

	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_Failure_Map() {
		Map map = new HashMap() {
			private static final long serialVersionUID = 1L;
			{
				put("key1", "ddd abc ddd");
				put("key2", "dddd");
			}
		};
		MatcherAssert.assertThat(map, matcher);
	}

	@Test
	public void testMatches_单值() {
		MatcherAssert.assertThat("ddd abc ddd", matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_单值_Failure() {
		MatcherAssert.assertThat("ddd ddd ddd", matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_值为Null_Failure() {
		MatcherAssert.assertThat(null, matcher);
	}
}
