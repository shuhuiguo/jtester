package org.jtester.hamcrest.iassert.common.intf;

import java.util.Collection;

import ext.jtester.hamcrest.Matcher;

/**
 * 数组或collection类型的对象容器断言
 * 
 * @author darui.wudr
 * 
 * @param <E>
 */

@SuppressWarnings("rawtypes")
public interface IListHasItemsAssert<E extends IAssert> {
	/**
	 * 数组或集合中的元素包含期望集合中列出的元素
	 * 
	 * @param coll
	 *            期望元素集合
	 * @return
	 */
	E hasItems(Collection coll);

	/**
	 * 数组或集合中的元素包含期望元素
	 * 
	 * @param value
	 *            期望元素
	 * @param values
	 *            期望元素
	 * @return
	 */
	E hasItems(Object value, Object... values);

	/**
	 * 数组或集合中的元素包含期望数组中列出的元素
	 * 
	 * @param values
	 *            期望元素数组
	 * @return
	 */
	E hasItems(Object[] values);

	/**
	 * 数组或集合中的元素包含期望数组中列出的布尔值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(boolean values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的byte类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(byte values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的char类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(char values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的short类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(short values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的int类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(int values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的long类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(long values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的float类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(float values[]);

	/**
	 * 数组或集合中的元素包含期望数组中列出的double类型值
	 * 
	 * @param values
	 * @return
	 */
	E hasItems(double values[]);

	/**
	 * 参数中列出的正则表达式可以被数组或集合中的元素满足
	 * 
	 * @param regular
	 *            期望的正则表达式
	 * @param regulars
	 *            期望的正则表达式
	 * @return
	 */
	E matchAnyItems(String regular, String... regulars);

	/**
	 * 数组或集合中所有的元素toString()必须满足所有列出期望的正则表达式
	 * 
	 * @param regular
	 *            期望的正则表达式
	 * @param regulars
	 *            期望的正则表达式
	 * @return
	 */
	E matchAllItems(String regular, String... regulars);

	/**
	 * 数组或集合中至少有一个元素满足列出期望
	 * 
	 * @param macher
	 * @param matchers
	 * @return
	 */
	E matchAnyItems(Matcher matcher, Matcher... matchers);

	/**
	 * 数组或集合中所有的元素满足列出期望
	 * 
	 * @param macher
	 * @param matchers
	 * @return
	 */
	E matchAllItems(Matcher matcher, Matcher... matchers);
}
