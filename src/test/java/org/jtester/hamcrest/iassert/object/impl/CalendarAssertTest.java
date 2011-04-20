package org.jtester.hamcrest.iassert.object.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mockit.Mocked;

import org.jtester.annotations.Inject;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class CalendarAssertTest extends JTester {
	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static Date date = null;
	private static Calendar cal = null;
	static {
		try {
			date = format.parse("2009-04-12 15:36:24");
			cal = Calendar.getInstance();
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private TestAppClaz testApp = new TestAppClaz();

	@Mocked
	@Inject(targets = "testApp")
	private IDateTest idate;

	public void yearIs() {
		want.calendar(cal).yearIs(2009).yearIs("2009");
		new Expectations() {
			{
				idate.setDate(the.date().yearIs(2009).yearIs("2009").wanted());
				idate.setCalendar(the.calendar().yearIs(2009).yearIs("2009").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void yearIs_failure1() {
		want.calendar(cal).yearIs(2009).yearIs("20091");
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void yearIs_failure2() {
		new Expectations() {
			{
				idate.setDate(the.date().yearIs(2009).yearIs("2008").wanted());
				idate.setCalendar(the.calendar().yearIs(2009).yearIs("2009").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	@Test(expectedExceptions = { AssertionError.class })
	public void yearIs_failure3() {
		new Expectations() {
			{
				idate.setDate(the.date().yearIs(2009).yearIs("2009").wanted());
				idate.setCalendar(the.calendar().yearIs(2008).wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	public void monthIs() {
		want.calendar(cal).monthIs(4).monthIs("04");
		new Expectations() {
			{
				idate.setDate(the.date().monthIs(4).monthIs("04").wanted());
				idate.setCalendar(the.calendar().monthIs(4).monthIs("4").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	public void dayIs() {
		want.calendar(cal).dayIs(12).dayIs("12");
		new Expectations() {
			{
				idate.setDate(the.date().dayIs(12).dayIs("12").wanted());
				idate.setCalendar(the.calendar().dayIs(12).dayIs("12").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	public void hourIs() {
		want.calendar(cal).hourIs(15).hourIs("15");
		new Expectations() {
			{
				idate.setDate(the.date().hourIs(15).hourIs("15").wanted());
				idate.setCalendar(the.calendar().hourIs(15).hourIs("15").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	public void minuteIs() {
		want.calendar(cal).minuteIs(36).minuteIs("36");
		new Expectations() {
			{
				idate.setDate(the.date().minuteIs(36).minuteIs("36").wanted());
				idate.setCalendar(the.calendar().minuteIs(36).minuteIs("36").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	public void secondIs() {
		want.calendar(cal).secondIs(24).secondIs("24");
		new Expectations() {
			{
				idate.setDate(the.date().secondIs(24).secondIs("24").wanted());
				idate.setCalendar(the.calendar().secondIs(24).secondIs("24").wanted());
			}
		};
		testApp.setTime(date, cal);
	}

	private static interface IDateTest {
		public void setDate(Date date);

		public void setCalendar(Calendar cal);
	}

	protected static class TestAppClaz {
		private IDateTest idate;

		public void setTime(Date date, Calendar cal) {
			idate.setDate(date);
			idate.setCalendar(cal);
		}

		public void setIdate(IDateTest idate) {
			this.idate = idate;
		}
	}

	@Test
	public void testFormatEqual() {
		want.calendar(cal).formatEqual("yyyy-MM-dd", "2009-04-12");
	}

	@Test
	public void testFormatEqual_断言消息() {
		try {
			want.calendar(cal).formatEqual("yyyy-MM-dd", "2010-01-03");
			want.fail("之前应该已经抛出异常");
		} catch (Throwable e) {
			String error = e.getMessage();
			want.string(error).contains("2009-04-12").contains("yyyy-MM-dd").contains("2010-01-03");
		}
	}
}
