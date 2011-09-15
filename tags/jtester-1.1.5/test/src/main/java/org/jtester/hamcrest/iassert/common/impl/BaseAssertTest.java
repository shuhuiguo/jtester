package org.jtester.hamcrest.iassert.common.impl;

import java.util.HashMap;
import java.util.Map;

import org.jtester.hamcrest.matcher.string.StringMode;
import org.jtester.json.encoder.beans.test.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class BaseAssertTest extends JTester {

	@Test(expectedExceptions = { AssertionError.class })
	public void testClazIs() {
		want.map(new HashMap<String, Object>()).clazIs(String.class);
	}

	public void testClazIs2() {
		want.map(new HashMap<String, Object>()).clazIs(Map.class);
	}

	@Test
	public void testAllOf() {
		want.string("test1").all(the.string().contains("test"), the.string().regular("\\w{5}"));
	}

	@Test
	public void testAllOf_iterable() {
		want.string("test1").all(the.string().contains("test"), the.string().regular("\\w{5}"));
	}

	@Test
	public void testAnyOf() {
		want.string("test1").any(the.string().contains("test4"), the.string().regular("\\w{5}"));
		want.string("test1").any(the.string().contains("test1"), the.string().regular("\\w{6}"));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testAnyOf_failure() {
		want.string("test1").any(the.string().contains("test4"), the.string().regular("\\w{6}"));
	}

	@Test
	public void testAnyOf_iterable() {
		want.string("test1").any(the.string().contains("test"), the.string().regular("\\w{6}"));
		want.string("test1").any(the.string().contains("test5"), the.string().regular("\\w{5}"));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void testAnyOf_iterable_failure() {
		want.string("test1").any(the.string().contains("test6"), the.string().regular("\\w{6}"));
	}

	@Test
			public void testEqToString() {
				User user = User.newInstance(124, "darui.wu");
				want.object(user).eqToString("User [id=124, name=darui.wu]");
			}

	@Test
			public void testMatchToString() {
				User user = User.newInstance(124, "darui.wu");
				want.object(user).matchToString(the.string().eq("User[id=124,name=darui.wu]", StringMode.IgnoreSpace));
			}
}
