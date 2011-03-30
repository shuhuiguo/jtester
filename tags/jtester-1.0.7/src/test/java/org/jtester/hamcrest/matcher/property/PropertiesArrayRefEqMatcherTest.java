package org.jtester.hamcrest.matcher.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class PropertiesArrayRefEqMatcherTest extends JTester {
	PropertiesArrayRefEqMatcher matcher = new PropertiesArrayRefEqMatcher(new String[] { "first", "last" },
			new String[][] { { "aaa", "bbb" }, { "ccc", "ddd" } });

	public void testMatches_属性集合集等于期望值() {
		List<User> users = Arrays.asList(new User("aaa", "bbb"), new User("ccc", "ddd"));
		MatcherAssert.assertThat(users, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_属性集合集_有个别属性值不等于期望值() {
		List<User> users = Arrays.asList(new User("aaa", "bbb"), new User("ccc", "dddd"));
		MatcherAssert.assertThat(users, matcher);
	}

	public void testMatches_单值对象_属性集合集等于期望值() {
		PropertiesArrayRefEqMatcher m = new PropertiesArrayRefEqMatcher(new String[] { "first", "last" },
				new String[][] { { "aaa", "bbb" } });
		MatcherAssert.assertThat(new User("aaa", "bbb"), m);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_单值对象_有属性不等于期望值() {
		PropertiesArrayRefEqMatcher m = new PropertiesArrayRefEqMatcher(new String[] { "first", "last" },
				new String[][] { { "aaa", "bbb" } });
		MatcherAssert.assertThat(new User("aaa", "bbbb"), m);
	}

	public void testMatches_Map列表_属性集合集等于期望值() {
		List<Map<String, String>> maps = maps("ccc", "ddd");

		MatcherAssert.assertThat(maps, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_Map列表_属性集合集中有属性不等于期望值() {
		List<Map<String, String>> maps = maps("ccc", "dddd");

		MatcherAssert.assertThat(maps, matcher);
	}

	private static List<Map<String, String>> maps(String first, String last) {
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("first", "aaa");
		map1.put("last", "bbb");
		maps.add(map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("first", first);
		map2.put("last", last);
		maps.add(map2);

		return maps;
	}
}
