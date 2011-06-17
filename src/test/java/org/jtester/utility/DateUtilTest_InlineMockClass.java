package org.jtester.utility;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mockit;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("unused")
@Test(groups = "jtester")
public class DateUtilTest_InlineMockClass extends JTester {
	@Test
	public void testCurrDateTimeStr_MockUp() throws Exception {
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

	@Test
	public void testCurrDateTimeStr_format() {
		Mockit.setUpMock(DateUtil.class, new Object() {
			@Mock
			public Date now() {
				Calendar cal = DateUtilTest.mockCalendar(2012, 1, 28);
				return cal.getTime();
			}
		});
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testCurrDateTimeStr_format_Exception() {
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("01/28/12 07:58:55");
	}
}
