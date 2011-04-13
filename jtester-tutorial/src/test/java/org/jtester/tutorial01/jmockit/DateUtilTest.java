package org.jtester.tutorial01.jmockit;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.MockClass;
import mockit.MockUp;
import mockit.Mockit;

import org.jtester.testng.JTester;
import org.jtester.tutorial01.utils.DateUtil;
import org.testng.annotations.Test;

@SuppressWarnings("unused")
@Test
public class DateUtilTest extends JTester {

	@Test
	public void testCurrDateStr() {
		new MockUp<DateUtil>() {
			@Mock
			Date now() {
				return getMockDate(2010, 1, 1);
			}
		};
		String currDate = DateUtil.currDateStr();
		want.string(currDate).isEqualTo("2010-01-01");
	}

	@Test
	public void testCurrDateTimeStr() {
		Mockit.setUpMock(DateUtil.class, new Object() {
			@Mock
			Date now() {
				return getMockDate(2010, 1, 1, 19, 20, 31);
			}
		});
		String currDate = DateUtil.currDateTimeStr();
		want.string(currDate).isEqualTo("2010-01-01 19:20:31");
	}

	@Test
	public void testCustomDateTimeStr() {
		Mockit.setUpMock(DateUtil.class, MockDateUtil.class);
		String datetime = DateUtil.currDateTimeStr("yy-MM-dd hh/mm/ss");
		want.string(datetime).isEqualTo("10-01-01 07/20/31");
	}

	@MockClass(realClass = DateUtil.class)
	public static class MockDateUtil {
		@Mock
		public static Date now() {
			return getMockDate(2010, 1, 1, 19, 20, 31);
		}
	}

	/**
	 * 返回一个指定的时间
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getMockDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 返回一个指定的时间
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date getMockDate(int year, int month, int day, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, hour, minute, second);
		return cal.getTime();
	}
}
