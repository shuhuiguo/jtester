package org.jtester.tutorial.biz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
	/**
	 * 返回当前日期的默认格式("yyyy-MM-dd")字符串
	 * 
	 * @return
	 */
	public static final String currDateStr() {
		return currDateTimeStr(now(), "yyyy-MM-dd");
	}

	public static final String currDateStr2() {
		return currDateTimeStr(new Date(), "yyyy-MM-dd");
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
}
