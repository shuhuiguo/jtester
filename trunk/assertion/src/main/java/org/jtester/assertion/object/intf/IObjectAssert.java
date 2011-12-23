package org.jtester.assertion.object.intf;

import org.jtester.assertion.common.intf.IBaseAssert;
import org.jtester.assertion.common.intf.IReflectionAssert;

/**
 * 通用对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface IObjectAssert extends IBaseAssert<Object, IObjectAssert>, IReflectionAssert<IObjectAssert> {

}
