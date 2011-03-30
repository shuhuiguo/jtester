/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.dynamicVariable;

import java.io.IOException;
import java.util.Map;

public interface DynamicVariables extends VariableResolver {
	Object get(String key);

	void put(String key, Object value);

	void putParameter(String key, Object value);

	boolean addFromPropertiesFile(String fileName);

	void addFromUnicodePropertyFile(String fileName) throws IOException;

	Map<Object, Object> getMap();

	void clearAll();

	String getAsString(String key);

	DynamicVariables popLocal();

	DynamicVariables top();
}
