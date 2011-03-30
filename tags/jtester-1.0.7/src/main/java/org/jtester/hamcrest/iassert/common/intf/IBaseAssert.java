package org.jtester.hamcrest.iassert.common.intf;

import org.hamcrest.Matcher;

/**
 * the basic asserting matcher
 * 
 * @author darui.wudr
 * 
 * @param <T>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public interface IBaseAssert<T, E extends IAssert<T, ?>> extends IAssert<T, E> {
	/**
	 * 断言对象等于期望的值
	 * 
	 * @param expected
	 *            期望值
	 * @return
	 */
	E isEqualTo(T expected);

	/**
	 * 断言对象等于期望的值
	 * 
	 * @param message
	 *            错误信息
	 * @param expected
	 *            期望值
	 * @return
	 */
	E isEqualTo(String message, T expected);

	/**
	 * 断言对象不等于期望的值
	 * 
	 * @param expected
	 *            期望值
	 * @return
	 */
	E notEqualTo(T expected);

	/**
	 * 断言对象可以在期望值里面找到
	 * 
	 * @param values
	 *            期望值
	 * @return
	 */
	E in(T... values);

	/**
	 * 断言对象不可以在期望值里面找到
	 * 
	 * @param values
	 *            期望值
	 * @return
	 */
	E notIn(T... values);

	/**
	 * 断言对象的类型等于期望类型
	 * 
	 * @param claz
	 *            期望类型
	 * @return
	 */
	E clazIs(Class claz);

	/**
	 * 断言对象符合matcher所定义的行为
	 * 
	 * @param matcher
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @return
	 */
	E is(Matcher matcher);

	/**
	 * 断言对象不符合matcher所定义的行为
	 * 
	 * @param matcher
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @return
	 */
	E not(Matcher matcher);

	/**
	 * 断言对象符合所有的对象行为定义
	 * 
	 * @param matcher1
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @param matcher2
	 * @param matchers
	 * @return
	 */
	E allOf(Matcher matcher1, Matcher matcher2, Matcher... matchers);

	/**
	 * 断言对象符合所有的对象行为定义
	 * 
	 * @param matchers
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @return
	 */
	E allOf(Iterable<? extends Matcher> matchers);

	/**
	 * 断言对象符合任一个对象行为定义
	 * 
	 * @param matcher1
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @param matcher2
	 * @param matchers
	 * @return
	 */
	E anyOf(Matcher matcher1, Matcher matcher2, Matcher... matchers);

	/**
	 * 断言对象符合任一个对象行为定义
	 * 
	 * @param matchers
	 *            对象行为定义，具体定义参见 org.hamcrest.Matcher
	 * @return
	 */

	E anyOf(Iterable<? extends Matcher> matchers);

	/**
	 * 断言对象和期望值是同一个对象
	 * 
	 * @param value
	 *            期望值
	 * @return
	 */
	E same(T value);

	/**
	 * 断言对象可以使任意的值
	 * 
	 * @return
	 */
	E any();

	/**
	 * 断言对象值等于null
	 * 
	 * @return
	 */
	E isNull();

	/**
	 * 断言对象值等于null
	 * 
	 * @return
	 */
	E isNull(String message);

	/**
	 * 断言对象值不等于null
	 * 
	 * @return
	 */
	E notNull();

	/**
	 * 断言对象值不等于null
	 * 
	 * @return
	 */
	E notNull(String message);
}
