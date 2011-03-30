package org.jtester.hamcrest.matcher.property;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "test")
public class AllPropertiesMatcherTest extends JTester {
	AllPropertiesMatcher matcher = new AllPropertiesMatcher("first", the.string().regular("\\w+\\d+\\w+"));

	@Test
	public void testMatches_List类型_所有对象的属性值都符合断言() {
		MatcherAssert.assertThat(users(), matcher);
	}

	@Test
	public void testMatches_数组对象_所有对象的属性值都符合断言() {
		User[] users = users().toArray(new User[0]);
		MatcherAssert.assertThat(users, matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_List类型_有属性值不符合断言() {
		List<User> users = users();
		users.add(new User("aasdf", "dfddd"));
		MatcherAssert.assertThat(users, matcher);
	}

	public void testMatches_单值对象_属性符合断言() {
		MatcherAssert.assertThat(new User("firs3445tname", ""), matcher);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_单值对象_属性不符合断言() {
		MatcherAssert.assertThat(new User("firs dddt name", ""), matcher);
	}

	static List<User> usersArr = null;

	private static List<User> users() {
		usersArr = new ArrayList<User>();
		usersArr.add(new User("firs3445tname", "lastname"));
		usersArr.add(new User("ee333ee", "ddddd"));

		return usersArr;
	}
}
