package org.jtester.assertion.object.intf;

import org.jtester.assertion.common.intf.IAssert;
import org.jtester.assertion.object.impl.JSONAssert;

public interface IJSONAssert extends IAssert<Object, JSONAssert> {
	/**
	 * json对象是集合
	 * 
	 * @return
	 */
	ICollectionAssert isJSONArray();

	/**
	 * json对象是key-value对象
	 * 
	 * @return
	 */
	IMapAssert isJSONMap();

	/**
	 * json对象是个简单对象
	 * 
	 * @return
	 */
	IStringAssert isSimple();
}
