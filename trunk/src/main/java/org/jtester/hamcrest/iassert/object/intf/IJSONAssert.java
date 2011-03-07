package org.jtester.hamcrest.iassert.object.intf;

import org.json.simple.JSONValue;
import org.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import org.jtester.hamcrest.iassert.object.impl.JSONAssert;

public interface IJSONAssert extends IBaseAssert<JSONValue, JSONAssert> {

	IJSONArrayAssert isArray();

	IJSONObjectAssert isSimple();

	// IJSONAssert matcher(String key, Matcher<?> matcher);
}
