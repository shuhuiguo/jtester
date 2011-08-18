package org.jtester.tutorial.debugit;

import org.jtester.core.IJTester;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleClassTest implements IJTester {
	@Test
	public void testIsAge18_AgeIs0() {
		try {
			SimpleClass.isAge18(0);
		} catch (Exception e) {
			String err = e.getMessage();
			want.string(err).isEqualTo("年龄不能小于一岁.");
		}
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testIsAge18_AgeLessThenZero() {
		SimpleClass.isAge18(0);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testIsAge18_AgeIsNull() {
		SimpleClass.isAge18(null);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testIsAge18_AgeIs201() {
		SimpleClass.isAge18(201);
	}

	@Test
	public void testIsAge18_AgeIs1() {
		boolean isAge18 = SimpleClass.isAge18(1);
		Assert.assertFalse(isAge18);
	}

	@Test
	public void testIsAge18_AgeIs17() {
		boolean isAge18 = SimpleClass.isAge18(17);
		Assert.assertFalse(isAge18);
	}

	@Test
	public void testIsAge18_AgeIs18() {
		boolean isAge18 = SimpleClass.isAge18(18);
		Assert.assertTrue(isAge18);
	}

	@Test
	public void testIsAge18_AgeIs19() {
		boolean isAge18 = SimpleClass.isAge18(19);
		Assert.assertFalse(isAge18);
	}

	@Test
	public void testIsAge18_AgeIs200() {
		boolean isAge18 = SimpleClass.isAge18(19);
		Assert.assertFalse(isAge18);
	}
}
