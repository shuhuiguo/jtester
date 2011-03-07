package org.jtester.hamcrest.iassert.object.intf;

import org.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import org.jtester.hamcrest.iassert.common.intf.IComparableAssert;

/**
 * 字符串对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface IStringAssert extends IBaseAssert<String, IStringAssert>, IComparableAssert<String, IStringAssert> {
	/**
	 * 断言字符串包含期望的字串${expected}
	 * 
	 * @param expected
	 *            期望的字串
	 * @return
	 */
	IStringAssert contains(String expected);

	/**
	 * 断言字符串以${expected}子串结尾
	 * 
	 * @param expected
	 *            期望的字串
	 * @return
	 */
	IStringAssert end(String expected);

	/**
	 * 断言字符串以${expected}子串开头
	 * 
	 * @param expected
	 *            期望的字串
	 * @return
	 */
	IStringAssert start(String expected);

	/**
	 * 断言字符串符合正则表达式${regex}
	 * 
	 * @param regex
	 *            期望的正则表达式
	 * @return
	 */
	IStringAssert regular(String regex);

	/**
	 * 断言字符串在忽略大小写的情况下等于期望值
	 * 
	 * @param string
	 *            期望值
	 * @return
	 */
	IStringAssert eqIgnoreCase(String string);

	/**
	 * 断言字符串在忽略前后空格的情况下等于期望值
	 * 
	 * @param string
	 *            期望值
	 * @return
	 */
	IStringAssert eqIgnorBlank(String string);

	/**
	 * 断言字符串不包含指定的子字符串
	 * 
	 * @param sub
	 * @return
	 */
	IStringAssert notContain(String sub);

	/**
	 * 断言字符串不是空白串
	 * 
	 * @return
	 */
	IStringAssert notBlank();
}
