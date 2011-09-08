package org.jtester.tutorial.asserter;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import ext.jtester.hamcrest.MatcherAssert;
import ext.jtester.hamcrest.core.IsEqual;

/**
 * 演示hamcrest matcher的工作原理<br>
 * ext.jtester.hamcrest.core.IsEqual
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class HamcrestIsEqualDemo extends JTester {

	@Test(description = "2个简单对象比较")
	public void testDemo_SimpleString() {
		String actual = "我是字符串";

		IsEqual matcher = new IsEqual("我是字符串");
		MatcherAssert.assertThat("简单字符串比较", actual, matcher);
	}

	@Test(description = "2个数组比较")
	public void testDemo_Array() {
		Object[] actual_arrays = new Object[] { "我是字符串", 1, true };
		Object[] expect_arrays = new Object[] { "我是字符串", 1, true };

		IsEqual matcher = new IsEqual(expect_arrays);
		MatcherAssert.assertThat("2个数组比较", actual_arrays, matcher);
	}
}
