package org.jtester.hamcrest.iassert.object.intf;

public interface IJSONObjectAssert extends IJSONAssert {
	/**
	 * json对象拥有值对 {key,value}...
	 * 
	 * @param key
	 * @param value
	 * @param strings
	 * @return
	 */
	IJSONObjectAssert hasKeyValues(String key, String value, String... strings);
}
