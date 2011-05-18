package org.jtester.tutorial.debugit;

import org.junit.Assert;
import org.testng.annotations.Test;

public class SimpleClassTest {

	@Test
	public void testIsAge18_1() {
		boolean isAge18 = SimpleClass.isAge18(17);
		Assert.assertFalse(isAge18);
	}

	@Test
	public void testIsAge18_2() {
		boolean isAge18 = SimpleClass.isAge18(18);
		Assert.assertTrue(isAge18);
	}

	@Test
	public void testIsAge18_3() {
		boolean isAge18 = SimpleClass.isAge18(19);
		Assert.assertFalse(isAge18);
	}

	@Test
	public void testIsAge18_4() {
		boolean isAge18 = SimpleClass.isAge18(null);
		Assert.assertFalse(isAge18);
	}
}
