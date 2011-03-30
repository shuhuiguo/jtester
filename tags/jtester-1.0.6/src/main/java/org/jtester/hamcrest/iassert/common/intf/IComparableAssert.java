package org.jtester.hamcrest.iassert.common.intf;

/**
 * 类型值可以比较大小的断言
 * 
 * @author darui.wudr
 * 
 * @param <E>
 */
public interface IComparableAssert<T extends Comparable<T>, E extends IAssert<T, ?>> {

	/**
	 * 断言对象小于期望值max
	 * 
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E lessThan(T max);

	/**
	 * 断言对象小于等于期望值max
	 * 
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E lessEqual(T max);

	/**
	 * 断言对象大于期望值min
	 * 
	 * @param min
	 *            期望最小值
	 * @return
	 */
	E greaterThan(T min);

	/**
	 * 断言对象大于等于期望值min
	 * 
	 * @param min
	 *            期望最小值
	 * @return
	 */
	E greaterEqual(T min);

	/**
	 * 断言对象在最小值和最大值之间(包括最大值和最小值)
	 * 
	 * @param min
	 *            期望最小值
	 * @param max
	 *            期望最大值
	 * @return
	 */
	E between(T min, T max);
}
