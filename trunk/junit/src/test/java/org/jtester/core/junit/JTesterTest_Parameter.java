package org.jtester.core.junit;

import java.util.Iterator;

import junit.framework.Assert;

import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.jtester.junit.JTester;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class JTesterTest_Parameter extends JTester {
	/**
	 * 验证jmockit带mock参数的方法可以正常运行
	 * 
	 * @param o
	 */
	@Test
	public void testWithMockPara(MockDto o) {
		Assert.assertNotNull(o);
	}

	/**
	 * 验证junit普通的测试方法可以正常运行
	 */
	@Test
	public void testNoParameter() {

	}

	/**
	 * 验证jTester数据驱动的方法可以正常运行<br>
	 * 数据来源是测试类中的静态方法
	 * 
	 * @param name
	 * @param index
	 */
	@DataFrom("dataWithParameter")
	@Test
	public void testWithParameter(String name, Integer index) {
		if (!name.equals("darui.wu") && !name.equals("jobs.he")) {
			throw new AssertionError();
		}
	}

	public static Iterator dataWithParameter() {
		return new DataIterator() {
			{
				data("darui.wu", 2);
				data("jobs.he", 1);
			}
		};
	}

	/**
	 * 验证jTester数据驱动的方法可以正常运行<br>
	 * 数据来源是另外一个类中的静态方法
	 * 
	 * @param name
	 * @param index
	 */
	@Test
	@DataFrom(value = "dataWithParameter", clazz = DataCase.class)
	public void testWithParameter_DataFromOtherClazz(String name) {

	}

	static class MockDto {
		private String name = "init";

		public void setName(String name) {

		}

		@Override
		public String toString() {
			return "MockDto [name=" + name + "]";
		}
	}
}
