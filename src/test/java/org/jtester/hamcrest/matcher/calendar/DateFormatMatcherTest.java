package org.jtester.hamcrest.matcher.calendar;

import java.util.Calendar;

import org.hamcrest.MatcherAssert;
import org.jtester.exception.JTesterException;
import org.jtester.testng.JTester;
import org.jtester.utility.DateUtilTest;
import org.testng.annotations.Test;

@Test(groups = "jtester")
@SuppressWarnings("unchecked")
public class DateFormatMatcherTest extends JTester {
	static Calendar cal = DateUtilTest.mockCalendar(2010, 1, 3);

	public void test_日历格式化字符串() {
		DateFormatMatcher matcher = new DateFormatMatcher("yyyy-MM-dd", "2010-01-03");
		MatcherAssert.assertThat(cal, matcher);
	}

	public void test_日历格式化字符串_断言消息() {
		DateFormatMatcher matcher = new DateFormatMatcher("yyyy-MM-dd", "2010-01-02");
		try {
			MatcherAssert.assertThat(cal, matcher);
			want.fail("之前应该已经抛出异常");
		} catch (Throwable e) {
			String error = e.getMessage();
			want.string(error).contains("2010-01-02").contains("yyyy-MM-dd").contains("2010-01-03");
		}
	}

	@Test(expectedExceptions = JTesterException.class)
	public void test_期望值为null的情况() {
		new DateFormatMatcher("yyyy-MM-dd", null);
	}

	@Test(expectedExceptions = JTesterException.class)
	public void test_实际值为null的情况() {
		DateFormatMatcher matcher = new DateFormatMatcher("yyyy-MM-dd", "2010-01-02");
		MatcherAssert.assertThat(null, matcher);
	}

	public void test_fomat异常的情况() {
		try {
			new DateFormatMatcher("yyyy-xx-dd", "2010-01-02");
			want.fail("之前应该已经抛出异常");
		} catch (Throwable e) {
			String message = e.getMessage();
			want.string(message).isEqualTo("illegal date fomat[yyyy-xx-dd].");
		}
	}
}
