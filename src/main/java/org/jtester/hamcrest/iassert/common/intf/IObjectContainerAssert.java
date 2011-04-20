package org.jtester.hamcrest.iassert.common.intf;

import java.util.Collection;

import org.hamcrest.Matcher;

/**
 * 数组或collection类型的对象容器断言
 * 
 * @author darui.wudr
 * 
 * @param <E>
 */

@SuppressWarnings("rawtypes")
public interface IObjectContainerAssert<E extends IAssert<?, ?>> {
	/**
	 * 数组长度或collection中元素个数等于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeIs(int size);

	/**
	 * 数组长度或collection中元素个数等于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeEq(int size);

	/**
	 * 数组长度或collection中元素个数大于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeGt(int size);

	/**
	 * 数组长度或collection中元素个数大于等于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeGe(int size);

	/**
	 * 数组长度或collection中元素个数小于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeLt(int size);

	/**
	 * 数组长度或collection中元素个数小于等于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeLe(int size);

	/**
	 * 数组长度或collection中元素个数不等于期望值
	 * 
	 * @param size
	 *            期望值
	 * @return
	 */
	E sizeNe(int size);

	/**
	 * 数组或集合中的元素包含期望集合中列出的元素
	 * 
	 * @param coll
	 *            期望元素集合
	 * @return
	 */
	E hasItems(Collection<?> coll);

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
	E hasItemMatch(String regular, String... regulars);

	/**
	 * 数组或集合中所有的元素toString()必须满足所有列出期望的正则表达式
	 * 
	 * @param regular
	 *            期望的正则表达式
	 * @param regulars
	 *            期望的正则表达式
	 * @return
	 */
	E allItemMatch(String regular, String... regulars);

	/**
	 * 数组或集合中至少有一个元素满足列出期望
	 * 
	 * @param macher
	 * @param matchers
	 * @return
	 */
	E hasItemMatch(Matcher matcher, Matcher... matchers);

	/**
	 * 数组或集合中所有的元素满足列出期望
	 * 
	 * @param macher
	 * @param matchers
	 * @return
	 */
	E allItemMatch(Matcher matcher, Matcher... matchers);
}
