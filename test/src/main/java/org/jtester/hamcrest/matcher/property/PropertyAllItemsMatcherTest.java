package org.jtester.hamcrest.matcher.property;

import java.util.ArrayList;
import java.util.List;

import ext.jtester.hamcrest.MatcherAssert;
import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("unchecked")
@Test(groups = { "jtester", "assertion" })
public class PropertyAllItemsMatcherTest extends JTester {
	PropertyAllItemsMatcher matcher = new PropertyAllItemsMatcher("first", the.string().regular("\\w+\\d+\\w+"));

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
		want.object(new User("firs3445tname", "")).propertyMatch("first", the.string().regular("\\w+\\d+\\w+"));
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_单值对象_属性不符合断言() {
		want.object(new User("firs dddt name", "")).propertyMatch("first", the.string().regular("\\w+\\d+\\w+"));
	}

	static List<User> usersArr = null;

	private static List<User> users() {
		usersArr = new ArrayList<User>();
		usersArr.add(new User("firs3445tname", "lastname"));
		usersArr.add(new User("ee333ee", "ddddd"));

		return usersArr;
	}
}
