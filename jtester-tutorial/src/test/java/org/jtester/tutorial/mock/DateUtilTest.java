package org.jtester.tutorial.mock;

import java.util.Calendar;
import java.util.Date;

import mockit.Mock;
import mockit.Mocked;

import org.jtester.testng.JTester;
import org.jtester.tutorial01.utils.DateUtil;
import org.testng.annotations.Test;

@SuppressWarnings("unused")
@Test(description = "简单mock功能演示")
public class DateUtilTest extends JTester {

	@Test(description = "演示通过new MockUp<T>()方式替换一个具体类的任何方法达到mock的效果")
	public void testCurrDateStr() {
		new MockUp<DateUtil>() {
			@Mock
			public Date now() {
				return getMockDate(2010, 1, 1);
			}
		};
		String currDate = DateUtil.currDateStr();
		want.string(currDate).isEqualTo("2010-01-01");
	}

	public void testExpectations() {
		// new MockUp<Date>() {
		// Date it;
		//
		// @Mock
		// public void $init() {
		// it = getMockDate(2010, 1, 1);
		// }
		// };
		new Expectations() {
			@Mocked
			Date mock;
			{
				new Date();
				result = getMockDate(2010, 1, 1);
			}
		};
		String currDate = DateUtil.currDateStr();
		want.string(currDate).isEqualTo("2010-01-01");
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
