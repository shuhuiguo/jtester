package org.jtester.hamcrest.iassert.common.intf;

import org.hamcrest.Matcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;

@SuppressWarnings("rawtypes")
public interface ICollArrayAssert<T, E extends IAssert<T, ?>> extends IAssert<T, E> {
	/**
	 * 指定property集合符合matcher的规则
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E propertyCollectionMatch(String property, Matcher matcher);

	/**
	 * 多个属性的集合等于指定值
	 * 
	 * @param properties
	 * @param expecteds
	 * @return
	 */
	E propertyCollectionRefEq(String[] properties, Object[][] expecteds, ReflectionComparatorMode... modes);

	/**
	 * 多个属性的集合等于指定值,忽略顺序
	 * 
	 * @param properties
	 * @param expecteds
	 * @return
	 */
	E propertyCollectionLenientEq(String[] properties, Object[][] expecteds);
}
