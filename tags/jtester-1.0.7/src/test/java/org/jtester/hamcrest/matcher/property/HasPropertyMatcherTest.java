package org.jtester.hamcrest.matcher.property;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class HasPropertyMatcherTest extends JTester {
	HasPropertyMatcher matcher = new HasPropertyMatcher("first", the.string().regular("\\w+\\d+\\w+"));

	@Test
	public void testMatches_List类型_有对象属性值符合断言() {
		List<User> users = Arrays.asList(new User("dfasdf", "eedaf"), new User("firs3445tname", "lastname"));
		MatcherAssert.assertThat(users, matcher);
	}

	@Test
	public void testMatches_数组对象_有对象属性值符合断言() {
		User[] users = new User[] { new User("dfasdf", "eedaf"), new User("firs3445tname", "lastname") };
		MatcherAssert.assertThat(users, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_List类型_所有属性值不符合断言() {
		List<User> users = Arrays.asList(new User("dfasdf", "eedaf"), new User("eaafsd", "lastname"));
		MatcherAssert.assertThat(users, matcher);
	}

	public void testMatches_单值对象_属性符合断言() {
		MatcherAssert.assertThat(new User("firs3445tname", ""), matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_单值对象_属性不符合断言() {
		MatcherAssert.assertThat(new User("firs dddt name", ""), matcher);
	}
}
