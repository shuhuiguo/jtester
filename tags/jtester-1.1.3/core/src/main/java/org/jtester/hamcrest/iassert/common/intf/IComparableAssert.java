package org.jtester.hamcrest.iassert.common.intf;

/**
 * 类型值可以比较大小的断言
 * 
 * @author darui.wudr
 * 
 * T extends Comparable<T>
 * @param <E>
 */
@SuppressWarnings("rawtypes")
public interface IComparableAssert<T, E extends IAssert> {

	/**
	 * 断言对象小于期望值max
	 * 
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E isLessThan(T max);

	/**
	 * 断言对象小于等于期望值max
	 * 
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E isLessEqual(T max);

	/**
	 * 断言对象大于期望值min
	 * 
	 * @param min
	 *            期望最小值
	 * @return
	 */
	E isGreaterThan(T min);

	/**
	 * 断言对象大于等于期望值min
	 * 
	 * @param min
	 *            期望最小值
	 * @return
	 */
	E isGreaterEqual(T min);

	/**
	 * 断言对象在最小值和最大值之间(包括最大值和最小值)
	 * 
	 * @param min
	 *            期望最小值
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E isBetween(T min, T max);
}
