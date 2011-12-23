package org.jtester.beans;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("serial")
public class DataMap extends LinkedHashMap<String, Object> {
	/**
	 * put(String,Object[])的快捷写法
	 * 
	 * @param key
	 * @param value1
	 * @param value2
	 * @param more
	 */
	public void put(String key, Object value1, Object value2, Object... more) {
		List<Object> list = new ArrayList<Object>();
		list.add(value1);
		list.add(value2);
		for (Object item : more) {
			list.add(item);
		}
		super.put(key, list.toArray(new Object[0]));
	}
}
