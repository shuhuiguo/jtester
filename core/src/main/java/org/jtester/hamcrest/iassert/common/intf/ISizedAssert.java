package org.jtester.hamcrest.iassert.common.intf;

@SuppressWarnings("rawtypes")
public interface ISizedAssert<E extends IAssert> {
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
}
