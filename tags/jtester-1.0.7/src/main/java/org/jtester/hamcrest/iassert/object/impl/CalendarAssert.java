package org.jtester.hamcrest.iassert.object.impl;

import org.jtester.hamcrest.iassert.common.impl.BaseAssert;
import org.jtester.hamcrest.iassert.object.intf.ICalendarAssert;
import org.jtester.hamcrest.matcher.LinkMatcher;
import org.jtester.hamcrest.matcher.calendar.CalendarEqualsMatcher;
import org.jtester.hamcrest.matcher.calendar.CalendarEqualsMatcher.CalendarFieldType;
import org.jtester.hamcrest.matcher.calendar.DateFormatMatcher;

public class CalendarAssert<T> extends BaseAssert<T, ICalendarAssert<T>> implements ICalendarAssert<T> {

	public CalendarAssert(Class<T> clazz) {
		super();
		this.value = null;
		this.type = AssertType.Expectations;
		this.link = new LinkMatcher<T>();
		this.assertClaz = ICalendarAssert.class;
		this.valueClaz = clazz;
	}

	public CalendarAssert(T value, Class<T> clazz) {
		super();
		this.type = AssertType.AssertThat;
		this.value = value;
		this.assertClaz = ICalendarAssert.class;
		this.valueClaz = clazz;
	}

	public ICalendarAssert<T> yearIs(int year) {
		return this.assertThat(year, CalendarFieldType.YEAR);
	}

	public ICalendarAssert<T> yearIs(String year) {
		return this.assertThat(year, CalendarFieldType.YEAR);
	}

	public ICalendarAssert<T> dayIs(int day) {
		return this.assertThat(day, CalendarFieldType.DATE);
	}

	public ICalendarAssert<T> dayIs(String day) {
		return this.assertThat(day, CalendarFieldType.DATE);
	}

	public ICalendarAssert<T> hourIs(int hour) {
		return this.assertThat(hour, CalendarFieldType.HOUR);
	}

	public ICalendarAssert<T> hourIs(String hour) {
		return this.assertThat(hour, CalendarFieldType.HOUR);
	}

	public ICalendarAssert<T> minuteIs(int minute) {
		return this.assertThat(minute, CalendarFieldType.MINUTE);
	}

	public ICalendarAssert<T> minuteIs(String minute) {
		return this.assertThat(minute, CalendarFieldType.MINUTE);
	}

	public ICalendarAssert<T> monthIs(int month) {
		return this.assertThat(month, CalendarFieldType.MONTH);
	}

	public ICalendarAssert<T> monthIs(String month) {
		return this.assertThat(month, CalendarFieldType.MONTH);
	}

	public ICalendarAssert<T> secondIs(int second) {
		return this.assertThat(second, CalendarFieldType.SECOND);
	}

	public ICalendarAssert<T> secondIs(String second) {
		return this.assertThat(second, CalendarFieldType.SECOND);
	}

	private ICalendarAssert<T> assertThat(int value, CalendarFieldType type) {
		CalendarEqualsMatcher matcher = new CalendarEqualsMatcher(value, type);
		return this.assertThat(matcher);
	}

	private ICalendarAssert<T> assertThat(String value, CalendarFieldType type) {
		CalendarEqualsMatcher matcher = new CalendarEqualsMatcher(Integer.valueOf(value), type);
		return this.assertThat(matcher);
	}

	public ICalendarAssert<T> formatEqual(String format, String value) {
		DateFormatMatcher matcher = new DateFormatMatcher(format, value);
		return this.assertThat(matcher);
	}
}
