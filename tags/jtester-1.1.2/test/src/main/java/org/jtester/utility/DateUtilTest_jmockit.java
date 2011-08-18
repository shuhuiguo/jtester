package org.jtester.utility;

import static org.jtester.utility.DateUtilTest.mockCalendar;

import java.util.Calendar;
import java.util.Date;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Mockit;

import org.jtester.testng.JTester;
import org.jtester.utility.DateUtilTest.MockDateUtil;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("unused")
public class DateUtilTest_jmockit extends JTester {

	@Test
	public void testCurrDateStr_mockExpectations() {
		new Expectations() {
			@Mocked("now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				result = mockCalendar().getTime();
			}
		};
		String str = DateUtil.currDateStr();
		want.string(str).isEqualTo("2010-02-12");
	}

	public void testCurrDateTimeStr_Delegate() {
		new Expectations() {
			@Mocked("now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				result = new Delegate() {
					public Date now() {
						Calendar cal = mockCalendar(2011, 1, 27);
						return cal.getTime();
					}
				};
			}
		};
		String str = DateUtil.currDateStr();
		want.string(str).isEqualTo("2011-01-27");
	}

	@Test
	public void testCurrDateStr() {
		Mockit.setUpMock(DateUtil.class, MockDateUtil.class);
		String str = DateUtil.currDateStr();
		want.string(str).isEqualTo("2010-02-12");
		Mockit.tearDownMocks();
	}

	@Test
	public void testCurrDateTimeStr() {
		Mockit.setUpMock(DateUtil.class, MockDateUtil.class);
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2010-02-12 19:58:55");
		Mockit.tearDownMocks();
	}

	@Test
	public void testCurrDateTimeStr_annotations() {
		new MockUp<DateUtil>() {
			@Mock
			Date now() {
				Calendar cal = mockCalendar(2011, 1, 27);
				return cal.getTime();
			}
		};
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2011-01-27 19:58:55");
	}

	@Test
	public void testCurrDateTimeStr_format() {
		Mockit.setUpMock(DateUtil.class, MockDateUtil.class);
		String str = DateUtil.currDateTimeStr("MM/dd/yy hh:mm:ss");
		want.string(str).isEqualTo("02/12/10 07:58:55");
		Mockit.tearDownMocks();
	}

	public void testCurrDateTimeStr_Delegate2() {
		new Expectations() {
			@Mocked("now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				result = new MyDateUtilNowDelegate();
			}
		};
		String str = DateUtil.currDateStr();
		want.string(str).isEqualTo("2311-01-27");
	}

	public static class MyDateUtilNowDelegate implements Delegate {
		public Date now() {
			Calendar cal = mockCalendar(2311, 1, 27);
			return cal.getTime();
		}
	}
}
