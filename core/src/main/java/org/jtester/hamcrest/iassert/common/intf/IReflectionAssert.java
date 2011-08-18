package org.jtester.hamcrest.iassert.common.intf;

import java.util.Map;

import ext.jtester.hamcrest.Matcher;

import org.jtester.hamcrest.matcher.property.reflection.EqMode;

/**
 * 针对对象属性进行断言
 * 
 * @author darui.wudr
 * 
 * @param <E>
 */

@SuppressWarnings("rawtypes")
public interface IReflectionAssert<E extends IAssert> {
	/**
	 * 断言对象指定的属性(property)值等于期望值<br>
	 * same as "eqByProperty(String, Object)"
	 * 
	 * @param property
	 *            对象属性名称
	 * @param expected
	 *            期望值
	 * @return
	 */
	E propertyEq(String property, Object expected, EqMode... modes);

	/**
	 * 断言对象的多个属性相等<br>
	 * same as "eqByProperties(String[],Object)"
	 * 
	 * @param properties
	 * @param expected
	 * @return
	 */
	E propertyEq(String[] properties, Object expected, EqMode... modes);

	/**
	 * 对象的属性值符合指定的断言器要求
	 * 
	 * @param property
	 * @param expected
	 * @return
	 */
	E propertyMatch(String property, Matcher matcher);

	/**
	 * 在反射比较的情况下，2者是相等的<br>
	 * two object is equals compared by reflected value.<br>
	 * 
	 * @param expected
	 *            期望对象
	 * @param modes
	 *            比较模式,详见org.jtester.hamcrest.matcher.property.reflection.EqMode
	 * 
	 * @return
	 */
	E reflectionEq(Object expected, EqMode... modes);

	/**
	 * 在忽略期望值是null或默认值的情况下二者是相等的
	 * 
	 * @param expected
	 * @return
	 */
	E eqIgnoreDefault(Object expected);

	/**
	 * 在忽略顺序，默认值，日期的情况下二者是相等的
	 * 
	 * @param expected
	 * @return
	 */
	E eqIgnoreAll(Object expected);

	/**
	 * 把实际对象按照Map中的key值取出来，进行反射比较<br>
	 * 如果对象的属性不在Map中，则不进行比较<br>
	 * 功能和
	 * "propertyEq(String[] properties, Object expected, EqMode... modes)"类似，
	 * 无非这里是把属性写到了Map中，方便一一对应
	 * 
	 * @param expected
	 * @return
	 */
	E reflectionEqWithMap(Map<String, Object> expected, EqMode... modes);
}
