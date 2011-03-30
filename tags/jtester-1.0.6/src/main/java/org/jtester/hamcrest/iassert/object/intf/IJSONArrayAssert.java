package org.jtester.hamcrest.iassert.object.intf;

import java.util.Collection;

import org.hamcrest.Matcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;

@SuppressWarnings("rawtypes")
public interface IJSONArrayAssert extends IJSONAssert {
	IJSONArrayAssert jsonSizeIs(int size);

	/**
	 * 断言对象指定的属性(property)值等于期望值
	 * 
	 * @param property
	 *            对象属性名称
	 * @param expected
	 *            期望值
	 * 
	 * @param modes
	 * @return
	 */
	IJSONArrayAssert keyValueEq(String key, Collection<String> expected, ReflectionComparatorMode... modes);

	/**
	 * 断言对象指定的属性(property)值等于期望值
	 * 
	 * @param key
	 *            对象属性名称
	 * @param expected
	 *            期望值
	 * @param modes
	 * @return
	 */
	IJSONArrayAssert keyValueEq(String key, String[] expected, ReflectionComparatorMode... modes);

	/**
	 * 断言对象指定的属性符合matcher定义的行为
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	IJSONArrayAssert keyValueMatch(String key, Matcher matcher);
}
