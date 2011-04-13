package org.jtester.tutorial01.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * ���ص�ǰ���ڵ�Ĭ�ϸ�ʽ("yyyy-MM-dd")�ַ���
	 * 
	 * @return
	 */
	public static final String currDateStr() {
		return currDateTimeStr(now(), "yyyy-MM-dd");
	}

	/**
	 * ���ص�ǰ����ʱ���Ĭ�ϸ�ʽ("yyyy-MM-dd mm:hh:SS")�ַ���
	 * 
	 * @return
	 */
	public static final String currDateTimeStr() {
		return currDateTimeStr(now(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * ���ص�ǰʱ��ĸ�ʽ���ַ���
	 * 
	 * @param format
	 * @return
	 */
	public static final String currDateTimeStr(String format) {
		return currDateTimeStr(now(), format);
	}

	/**
	 * ����ָ��ʱ��ĸ�ʽ���ַ���
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
