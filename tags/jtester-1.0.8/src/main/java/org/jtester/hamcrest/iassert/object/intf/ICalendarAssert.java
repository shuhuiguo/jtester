package org.jtester.hamcrest.iassert.object.intf;

import org.jtester.hamcrest.iassert.common.intf.IBaseAssert;

/**
 * 日历类型对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface ICalendarAssert<T> extends IBaseAssert<T, ICalendarAssert<T>> {
	/**
	 * 日历值字符串格式化后等于期望值value
	 * 
	 * @param format
	 * @param value
	 * @return
	 */
	ICalendarAssert<T> formatEqual(String format, String value);

	/**
	 * 日历值的年等于期望值
	 * 
	 * @param year
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> yearIs(int year);

	/**
	 * 日历值的年等于期望值
	 * 
	 * @param year
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> yearIs(String year);

	/**
	 * 日历值的月份等于期望值
	 * 
	 * @param month
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> monthIs(int month);

	/**
	 * 日历值的月份等于期望值
	 * 
	 * @param month
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> monthIs(String month);

	/**
	 * 日历值的日期等于期望值
	 * 
	 * @param day
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> dayIs(int day);

	/**
	 * 日历值的日期等于期望值
	 * 
	 * @param day
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> dayIs(String day);

	/**
	 * 日历值的小时(24小时制)等于期望值
	 * 
	 * @param hour
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> hourIs(int hour);

	/**
	 * 日历值的小时(24小时制)等于期望值
	 * 
	 * @param hour
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> hourIs(String hour);

	/**
	 * 日历值的分钟等于期望值
	 * 
	 * @param minute
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> minuteIs(int minute);

	/**
	 * 日历值的分钟等于期望值
	 * 
	 * @param minute
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> minuteIs(String minute);

	/**
	 * 日历值的秒等于期望值
	 * 
	 * @param second
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> secondIs(int second);

	/**
	 * 日历值的秒等于期望值
	 * 
	 * @param second
	 *            期望值
	 * @return
	 */
	ICalendarAssert<T> secondIs(String second);
}
