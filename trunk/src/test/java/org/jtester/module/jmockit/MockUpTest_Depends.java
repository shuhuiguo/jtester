package org.jtester.module.jmockit;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.MockUp;

import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.utility.DateUtil;
import org.jtester.utility.DateUtilTest;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

/**
 * 验证new MockUp<T> 的作用域
 * 
 * @author darui.wudr
 * 
 */
@Test(groups = "jtester")
@SuppressWarnings("unused")
@SpringApplicationContext("org/jtester/fortest/spring/data-source.xml")
@AutoBeanInject
public class MockUpTest_Depends extends JTester {
	@SpringBeanByName(claz = MyImpl.class)
	MyIntf myIntf;

	@Test
	public void testStaticMethod_mock() {
		new MockUp<DateUtil>() {
			@Mock
			public Date now() {
				Calendar cal = DateUtilTest.mockCalendar(2012, 1, 28);
				return cal.getTime();
			}
		};
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	@Test(dependsOnMethods = "testStaticMethod_mock", expectedExceptions = AssertionError.class)
	public void testStaticMethod_unmock() {
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	public void testMehtod_unmock_beforeMock() {
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("hello");
	}

	@Test(dependsOnMethods = "testMehtod_unmock_beforeMock")
	public void testMethod_mock() {
		new MockUp<MyImpl>() {
			@Mock
			public String hello() {
				return "hello mock!";
			}
		};
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("hello mock!");
	}

	@Test(dependsOnMethods = "testMethod_mock")
	public void testMehtod_unmock_afterMock() {
		String hello = myIntf.hello();
		want.string(hello).isEqualTo("hello");
	}

	public static interface MyIntf {
		String hello();
	}

	public static class MyImpl implements MyIntf {
		public String hello() {
			return "hello";
		}
	}
}
