package org.jtester.hamcrest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class TestArrayAssert extends JTester {
	@Test
	public void test1() {
		want.array(new String[] { "aaaa", "bbbb" }).hasItems("aaaa", "bbbb");
		want.array(new int[] { 1, 2 }).hasItems(1);
		want.array(new double[] { 1, 2.0d }).hasItems(1d);

		want.array(new int[] { 1, 2, 3 }).hasItems(Arrays.asList(1, 3));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void test2() {
		want.array(new int[] { 1, 2, 3 }).hasItems(Arrays.asList(1, 4));
	}

	public void testPropertyMatch() {
		User user = User.mock();
		want.object(user).propertyMatch("addresses", the.collection().propertyEq("id", new int[] { 2, 3 }));
	}

	public void testPropertyMatcher2() {
		User user = User.mock();

		want.object(user).propertyMatch("addresses", the.collection().propertyEq("id", new int[] { 2, 3 }));
	}

	protected static List<User> getUsers() {
		List<User> users = new ArrayList<User>();

		return users;
	}
}
