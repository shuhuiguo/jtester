package org.jtester.hamcrest.iassert.object.impl;

import java.util.Arrays;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class CollectionAssertTest_HasItem extends JTester {
	@Test
	public void hasItems_test1() {
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaa");
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaa", "ccc");
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(Arrays.asList("aaa", "ccc"));
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(new String[] { "aaa", "ccc" });
		want.collection(Arrays.asList(1, 2, 4)).sizeEq(3).hasItems(new int[] { 1, 4 });

		want.collection(Arrays.asList(1, 2, 4)).hasItems(1, 4).sizeLt(4);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_test2() {
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaad");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_test3() {
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems("aaa", "ccc", "dddd");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_test4() {
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(Arrays.asList("aaac"));
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_test5() {
		want.collection(Arrays.asList("aaa", "bbb", "ccc")).hasItems(new String[] { "aaad", "ccc" });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_test6() {
		want.collection(Arrays.asList(1, 2, 4)).hasItems(1, 5);
	}

	public void hasItems_bool() {
		want.array(new boolean[] { true, true }).hasItems(new boolean[] { true });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_boolFailure() {
		want.array(new boolean[] { true, true }).hasItems(new boolean[] { false });
	}

	public void hasItems_byte() {
		want.array(new byte[] { Byte.MAX_VALUE, Byte.MIN_VALUE }).hasItems(new byte[] { Byte.MAX_VALUE });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_byteFail() {
		want.array(new byte[] { Byte.MAX_VALUE, Byte.MAX_VALUE }).hasItems(new byte[] { Byte.MIN_VALUE });
	}

	public void hasItems_char() {
		want.array(new char[] { 'a', 'b' }).hasItems(new char[] { 'a' });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_charFail() {
		want.array(new char[] { 'a', 'b' }).hasItems(new char[] { 'c' });
	}

	public void hasItems_short() {
		want.array(new short[] { 1, 2 }).hasItems(new short[] { 1 });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_shortFail() {
		want.array(new short[] { 1, 2 }).hasItems(new short[] { 3 });
	}

	public void hasItems_long() {
		want.array(new long[] { 1L, 2L }).hasItems(new long[] { 1L });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_longFail() {
		want.array(new long[] { 1, 2 }).hasItems(new long[] { 3L });
	}

	public void hasItems_float() {
		want.array(new float[] { 1f, 2f }).hasItems(new float[] { 1f });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_floatFail() {
		want.array(new float[] { 1f, 2f }).hasItems(new float[] { 3f });
	}

	public void hasItems_double() {
		want.array(new double[] { 1d, 2d }).hasItems(new double[] { 1d });
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void hasItems_doubleFail() {
		want.array(new double[] { 1d, 2d }).hasItems(new double[] { 3d });
	}
}
