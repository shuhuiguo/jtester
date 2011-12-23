package org.jtester.assertion.object.intf;

import java.util.Collection;

import org.jtester.assertion.common.intf.IBaseAssert;
import org.jtester.assertion.common.intf.IListAssert;
import org.jtester.assertion.common.intf.IListHasItemsAssert;
import org.jtester.assertion.common.intf.IReflectionAssert;

/**
 * 集合类型对象断言接口
 * 
 * @author darui.wudr
 * 
 */

@SuppressWarnings("rawtypes")
public interface ICollectionAssert extends IBaseAssert<Collection, ICollectionAssert>,
		IReflectionAssert<ICollectionAssert>, IListHasItemsAssert<ICollectionAssert>,
		IListAssert<Collection, ICollectionAssert> {
}
