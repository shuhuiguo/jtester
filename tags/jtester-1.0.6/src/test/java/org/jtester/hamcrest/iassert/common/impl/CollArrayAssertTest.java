package org.jtester.hamcrest.iassert.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.jtester.fortest.beans.User;
import org.jtester.testng.JTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class CollArrayAssertTest extends JTester {
	List<User> users;

	@BeforeMethod
	public void initData() {
		users = new ArrayList<User>();
		users.add(new User("first1", "last1"));
		users.add(new User("first2", "last2"));
	}

	@Test
	public void testPropertyCollectionLenientEq() {
		String[][] expecteds = new String[][] { { "first1", "last1" }, { "first2", "last2" } };
		want.collection(users).propertyCollectionLenientEq(new String[] { "first", "last" }, expecteds);
	}
}
