package org.jtester.hamcrest.matcher.property;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class PropertiesArrayMatcherTest extends JTester {

	@Test
	public void testMatches() {
		PropertiesArrayMatcher matcher = new PropertiesArrayMatcher(new String[] { "first", "last" }, new Object[] {
				"dfasdf", "eedaf" });
		MatcherAssert.assertThat(new User("dfasdf", "eedaf"), matcher);
	}

	@Test
	public void testMatches_使用断言() {
		PropertiesArrayMatcher matcher = new PropertiesArrayMatcher(new String[] { "first", "last" }, new Object[] {
				"dfasdf", the.string().contains("124") });
		MatcherAssert.assertThat(new User("dfasdf", "ee124daf"), matcher);
	}

	@Test
	public void testMatches_对象是数组() {
		List<User> users = Arrays.asList(new User("dfa123sdf", "abc"), new User("firs123tname", "abc"));
		PropertiesArrayMatcher matcher = new PropertiesArrayMatcher(new String[] { "first", "last" }, new Object[] {
				the.string().contains("123"), "abc" });
		MatcherAssert.assertThat(users, matcher);
	}

	public void testMatches_Map对象() {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("first", "aaaaa 123 ddd");
		maps.put("last", "abc");
		PropertiesArrayMatcher matcher = new PropertiesArrayMatcher(new String[] { "first", "last" }, new Object[] {
				the.string().contains("123"), "abc" });
		MatcherAssert.assertThat(maps, matcher);
	}
}
