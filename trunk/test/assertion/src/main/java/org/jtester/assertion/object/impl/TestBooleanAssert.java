package org.jtester.assertion.object.impl;

import org.jtester.IAssertion;
import org.junit.Test;

public class TestBooleanAssert implements IAssertion {

	@Test
	public void test1() {
		want.bool(true).isEqualTo(true);
		want.bool(true).is(true);
	}

	@Test(expected = AssertionError.class)
	public void test2() {
		want.bool(true).is(false);
	}

	@Test(expected = AssertionError.class)
	public void test3() {
		// fail();
		want.fail();
	}
}
