package org.jtester.assertion.object.intf;

import org.jtester.assertion.common.intf.IBaseAssert;
import org.jtester.assertion.common.intf.IListAssert;
import org.jtester.assertion.common.intf.IListHasItemsAssert;
import org.jtester.assertion.common.intf.IReflectionAssert;

/**
 *数组对象断言接口
 * 
 * @author darui.wudr
 * 
 */
public interface IArrayAssert extends IBaseAssert<Object[], IArrayAssert>, IListHasItemsAssert<IArrayAssert>,
		IReflectionAssert<IArrayAssert>, IListAssert<Object[], IArrayAssert> {
	
}
