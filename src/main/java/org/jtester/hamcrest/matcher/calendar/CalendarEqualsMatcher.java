package org.jtester.hamcrest.matcher.calendar;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jtester.exception.JTesterException;

public class CalendarEqualsMatcher extends BaseMatcher<Calendar> {
	private int expected;

	private CalendarFieldType type;

	public CalendarEqualsMatcher(int expected, CalendarFieldType type) {
		this.expected = expected;
		this.type = type;
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			throw new JTesterException("the actual value can't be null");
		}
		Calendar cal = null;

		if (actual instanceof Calendar) {
			cal = (Calendar) actual;
		} else if (actual instanceof Date) {
			cal = Calendar.getInstance();
			cal.setTime((Date) actual);
		} else {
			throw new JTesterException(
					"the actual value must be a java.util.Date instance or a java.util.Calendar instance");
		}
		int value = cal.get(type.calendarField());
		if (type == CalendarFieldType.MONTH) {
			return expected == (value + 1);
		} else {
			return expected == value;
		}
	}

	public void describeTo(Description description) {
		description.appendText(String.format(type.description(), this.expected));
	}

	public static enum CalendarFieldType {
		YEAR {
			@Override
			public int calendarField() {
				return Calendar.YEAR;
			}

			@Override
			public String description() {
				return "the year of expected time must equal to %d";
			}
		},
		MONTH {
			@Override
			public int calendarField() {
				return Calendar.MONTH;
			}

			@Override
			public String description() {
				return "the month of expected time must equal to %d";
			}
		},
		DATE {
			@Override
			public int calendarField() {
				return Calendar.DAY_OF_MONTH;
			}

			@Override
			public String description() {
				return "the day/month of expected time must equal to %d";
			}
		},
		HOUR {
			@Override
			public int calendarField() {
				return Calendar.HOUR_OF_DAY;
			}

			@Override
			public String description() {
				return "the hour of expected time must equal to %d";
			}
		},
		MINUTE {
			@Override
			public int calendarField() {
				return Calendar.MINUTE;
			}

			@Override
			public String description() {
				return "the minute of expected time must equal to %d";
			}
		},
		SECOND {
			@Override
			public int calendarField() {
				return Calendar.SECOND;
			}

			@Override
			public String description() {
				return "the second of expected time must equal to %d";
			}
		};

		public abstract int calendarField();

		public abstract String description();
	}
}
