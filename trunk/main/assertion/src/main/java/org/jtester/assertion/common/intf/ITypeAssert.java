package org.jtester.assertion.common.intf;

import java.util.Calendar;

import org.jtester.assertion.object.intf.IArrayAssert;
import org.jtester.assertion.object.intf.IBooleanAssert;
import org.jtester.assertion.object.intf.IByteAssert;
import org.jtester.assertion.object.intf.IDateAssert;

@SuppressWarnings("rawtypes")
public interface ITypeAssert<T, E extends IAssert> extends IAssert<T, E> {
	/**
	 * 断言对象是数字类型，并将断言器转换成数组断言器
	 * 
	 * @param claz
	 * @return
	 */
	IArrayAssert typeIsArray();

	IBooleanAssert typeIsBool();

	IByteAssert typeIsByte();

	IDateAssert<Calendar> typeIsCalendar();

	// TODO ...
}
