package org.jtester.hamcrest.iassert.object.intf;

import org.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import org.jtester.hamcrest.iassert.common.intf.ICollArrayAssert;
import org.jtester.hamcrest.iassert.common.intf.IObjectContainerAssert;
import org.jtester.hamcrest.iassert.common.intf.IReflectionAssert;

/**
 *数组对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface IArrayAssert extends IBaseAssert<Object[], IArrayAssert>, IObjectContainerAssert<IArrayAssert>,
		IReflectionAssert<IArrayAssert>, ICollArrayAssert<Object[], IArrayAssert> {
}
