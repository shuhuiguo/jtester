package org.jtester.utility;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
@SuppressWarnings("unused")
public class DateUtilTest extends JTester {

	@Test
	public void testToDateTimeStr() {
		String dateStr = DateUtil.toDateTimeStr(getMockDate(), "yyyy-MM-dd HH:mm:ss");
		want.string(dateStr).isEqualTo("2010-02-12 19:58:55");
	}

	public void testToDateTimeStr_MockitExpectation() {
		new NonStrictExpectations() {
			@Mocked(methods = "now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				returns(getMockDate());
			}
		};
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2010-02-12 19:58:55");
	}

	public void testToDateTimeStr_MockitExpectation_returnSequence() {
		new NonStrictExpectations() {
			@Mocked(methods = "now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				returns(getMockDate(), mockCalendar(2013, 2, 12).getTime());
			}
		};
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2010-02-12 19:58:55");
		str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2013-02-12 19:58:55");
	}

	public void testToDateTimeStr_MockitExpectation2() {
		new NonStrictExpectations() {
			@Mocked(methods = "now")
			DateUtil dateUtil;
			{
				DateUtil.now();
				returns(mockCalendar(2015, 6, 25).getTime());
			}
		};
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2015-06-25 19:58:55");
	}

	public void testToDateTimeStr_dynamicPartialMock() {
		new Expectations(DateUtil.class) {
			{
				DateUtil.now();
				returns(mockCalendar(2009, 6, 25).getTime());
			}
		};
		String str = DateUtil.currDateTimeStr();
		want.string(str).isEqualTo("2009-06-25 19:58:55");
	}

	public static final Date getMockDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 1, 12, 19, 58, 55);
		return cal.getTime();
	}

	public static class MockDateUtil {
		@Mock
		public static final Date now() {
			Calendar cal = mockCalendar();
			return cal.getTime();
		}
	}

	public static Calendar mockCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(2010, 1, 12, 19, 58, 55);
		return cal;
	}

	public static Calendar mockCalendar(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 19, 58, 55);
		return cal;
	}

	@Test
	public void testParse() {
		Date date = DateUtil.parse("2010-10-20");
		want.date(date).eqByFormat("2010/10/20", "yyyy/MM/dd");
	}

	@Test
	public void testParse_包含毫秒() {
		Date date = DateUtil.parse("2010-10-20 18:20:36.231");
		want.date(date).eqByFormat("2010/10/20 06:20:36.231", "yyyy/MM/dd hh:mm:ss.SSS");
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void testParse_格式异常() {
		DateUtil.parse("2010-10/20 18:20:36.231");
	}
}
