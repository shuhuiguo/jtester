package org.jtester.hamcrest.iassert.common.intf;

import org.hamcrest.Matcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;

/**
 * 扩展自unitils的断言，针对对象属性进行断言
 * 
 * @author darui.wudr
 * 
 * @param <E>
 */

@SuppressWarnings("rawtypes")
public interface IReflectionAssert<E extends IAssert<?, ?>> {
	/**
	 * 断言对象和期望对象在某种形式上是相同的
	 * 
	 * @param expected
	 *            期望对象
	 * @param modes
	 *            比较模式,详见org.unitils.reflectionassert.ReflectionComparatorMode
	 * @return
	 */
	E reflectionEq(Object expected, ReflectionComparatorMode... modes);

	/**
	 * reflectionEq指定比较模式为<br>
	 * org.unitils.reflectionassert.ReflectionComparatorMode.IGNORE_DEFAULTS + <br>
	 * org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER<br>
	 * 的快捷方法
	 * 
	 * @param expected
	 * @return
	 */
	E lenientEq(Object expected);

	/**
	 * 断言对象指定的属性(property)值等于期望值
	 * 
	 * @param property
	 *            对象属性名称
	 * @param expected
	 *            期望值
	 * @return
	 */
	E propertyEq(String property, Object expected);

	/**
	 * 断言对象指定的属性(property)值等于期望值
	 * 
	 * @param properties
	 * @param expected
	 * @return
	 */
	E propertyEq(String[] properties, Object[] expected);

	/**
	 * 断言对象的多个属性值集合中的每个属性值一一符合期望（或等于期望值）
	 * 
	 * @param properties
	 * @param expected
	 * @return
	 */
	E propertiesMatch(String[] properties, Object[] expected);

	/**
	 * 断言对象指定的属性(或属性集合)符合matcher定义的行为
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E propertyMatch(String property, Matcher matcher);

	/**
	 * 断言对象指定的属性值至少有一个符合matcher定义的行为
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E hasPropertyMatch(String property, Matcher matcher);

	/**
	 * 断言集合对象的属性值列表每个属性值都符合期望
	 * 
	 * @param property
	 * @param matcher
	 * @return
	 */
	E allPropertyMatch(String property, Matcher matcher);
}
