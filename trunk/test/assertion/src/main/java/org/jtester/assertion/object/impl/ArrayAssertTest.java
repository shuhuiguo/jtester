package org.jtester.assertion.object.impl;

import org.jtester.IAssertion;
import org.junit.Test;

public class ArrayAssertTest implements IAssertion {
	@Test
	public void hasItems() {
		want.array(new String[] { "first item", "second item", "third item" }).hasAllItems("first item", "second item");
	}
}
