package org.jtester.hamcrest.iassert.common.intf;

import org.jtester.hamcrest.matcher.property.reflection.EqMode;

import ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public interface IListAssert<T, E extends IAssert> extends IAssert<T, E>, ISizedAssert<E> {
	/**
	 * 
	 * @param expected
	 * @param modes
	 * @return
	 */
	E isEqualTo(Object expected, EqMode... modes);

	/**
	 * 指定property属性集合中任意一个对象符合断言
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E matchAnyProperty(String property, Matcher matcher);

	/**
	 * 指定property属性集合中所有对象都符合断言
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E matchAllProperty(String property, Matcher matcher);

	/**
	 * 忽略顺序的情况下二者相等
	 * 
	 * @param expected
	 * @return
	 */
	E eqIgnoreOrder(Object expected);
}
