package org.jtester.utility;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.Mockit;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class DateUtilTest_MockItSetUp extends JTester {
	public static class MockDateUtil {
		@Mock
		public static Date now() {
			Calendar cal = DateUtilTest.mockCalendar(2012, 1, 28);
			return cal.getTime();
		}
	}

	@Test
	public void testCurrDateTimeStr_format() {
		Mockit.setUpMock(DateUtil.class, MockDateUtil.class);
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testCurrDateTimeStr_format_Exception() {
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	@Test
	public void testCurrDateTimeStr_dynamicMock() {
		new Expectations(DateUtil.class) {
			{
				when(DateUtil.now()).thenReturn(DateUtilTest.mockCalendar(2014, 1, 28).getTime());
			}
		};
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/14 07:58:55");
	}
}
