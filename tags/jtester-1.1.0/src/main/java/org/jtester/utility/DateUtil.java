package org.jtester.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 返回当前日期的默认格式("yyyy-MM-dd")字符串
	 * 
	 * @return
	 */
	public static final String currDateStr() {
		return currDateTimeStr(now(), "yyyy-MM-dd");
	}

	/**
	 * 返回当前日期时间的默认格式("yyyy-MM-dd mm:hh:SS")字符串
	 * 
	 * @return
	 */
	public static final String currDateTimeStr() {
		return currDateTimeStr(now(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 返回当前时间的格式化字符串
	 * 
	 * @param format
	 * @return
	 */
	public static final String currDateTimeStr(String format) {
		return currDateTimeStr(now(), format);
	}

	/**
	 * 返回指定时间的格式化字符串
	 * 
	 * @param format
	 * @return
	 */
	public static final String currDateTimeStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static final Date now() {
		return new Date();
	}

	private static DateFormat df_default = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static DateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat df_date = new SimpleDateFormat("yyyy-MM-dd");

	public static final Date parse(String str) {
		if (StringHelper.isBlankOrNull(str)) {
			throw new RuntimeException("parse date string can't be empty.");
		}
		Date date = null;
		try {
			try {
				date = df_default.parse(str);
			} catch (ParseException e1) {
				try {
					date = df_time.parse(str);
				} catch (ParseException e2) {
					date = df_date.parse(str);
				}
			}
		} catch (Throwable e) {
			String error = "can't parse datetime from string[" + str + "].";
			throw new RuntimeException(error, e);
		}
		if (date == null) {
			throw new RuntimeException("can't parse datetime from string[" + str + "].");
		}
		return date;
	}
}
