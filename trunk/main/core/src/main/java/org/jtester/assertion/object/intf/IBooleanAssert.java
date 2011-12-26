package org.jtester.assertion.object.intf;

import org.jtester.assertion.common.intf.IBaseAssert;

/**
 * 布尔值断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface IBooleanAssert extends IBaseAssert<Boolean, IBooleanAssert> {
	IBooleanAssert is(boolean bl);

	IBooleanAssert is(String description, boolean bl);
}
