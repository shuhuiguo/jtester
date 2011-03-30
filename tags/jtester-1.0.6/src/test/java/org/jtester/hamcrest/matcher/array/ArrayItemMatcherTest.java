package org.jtester.hamcrest.matcher.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.jtester.hamcrest.matcher.array.ArrayItemMatcher.ItemMatcherType;
import org.jtester.hamcrest.mockito.Matches;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;


@SuppressWarnings({ "unchecked", "rawtypes" })
@Test(groups = "jtester")
public class ArrayItemMatcherTest extends JTester {
	Matcher m1 = the.string().contains("abc");
	ArrayItemMatcher matcher = new ArrayItemMatcher(m1, ItemMatcherType.OR);

	@Test
	public void testMatches_集合() {
		MatcherAssert.assertThat(Arrays.asList("ddd abc ddd", "ddddd"), matcher);
	}

	@Test
	public void testMatches_集合_正则表达式() {
		Matcher<?> regular = new Matches("abc[2-4]{2,4}abc");
		ArrayItemMatcher matcher = new ArrayItemMatcher(regular, ItemMatcherType.OR);
		MatcherAssert.assertThat(Arrays.asList("abc234abc", "ddddd"), matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_集合_Failure() {
		MatcherAssert.assertThat(Arrays.asList("ddd ddd ddd", "ddddd"), matcher);
	}

	@Test
	public void testMatches_数组() {
		MatcherAssert.assertThat(new String[] { "ddd abc ddd", "ddddd" }, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_数组_Failure() {
		MatcherAssert.assertThat(new String[] { "ddd ddd ddd", "ddddd" }, matcher);
	}

	@Test
	public void testMatches_Map() {
		Matcher m1 = the.string().contains("abc");
		ArrayItemMatcher matcher = new ArrayItemMatcher(m1, ItemMatcherType.OR);
		Map map = new HashMap() {
			private static final long serialVersionUID = 1L;
			{
				put("key1", "ddd abc ddd");
				put("key2", "dddd");
			}
		};
		MatcherAssert.assertThat(map, matcher);

	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_Map_Failure() {
		Map map = new HashMap() {
			private static final long serialVersionUID = 1L;
			{
				put("key1", "ddd ddd ddd");
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
