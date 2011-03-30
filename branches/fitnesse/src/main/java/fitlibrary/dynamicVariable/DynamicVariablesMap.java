/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.dynamicVariable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import fit.Fixture;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.Pair;

public abstract class DynamicVariablesMap implements DynamicVariables {
	private Map<Object, Object> map = new HashMap<Object, Object>(System.getProperties());

	public DynamicVariablesMap() {
		//
	}

	public DynamicVariablesMap(DynamicVariables dynamicVariables) {
		map = new HashMap<Object, Object>(dynamicVariables.getMap());
	}

	// @Override
	public Pair<String, Tables> resolve(String locator) {
		String result = locator;
		Tables tables = TableFactory.tables();
		int pos = 0;
		int loops = 0;
		while (true) {
			pos = result.indexOf("@{", pos);
			if (pos < 0)
				break;
			if (loops++ > 10000)
				return new Pair<String, Tables>("INFINITE SUBSTITUTION!", tables);
			int end = result.indexOf("}", pos);
			if (end >= 0) {
				Object substitute = get(result.substring(pos + 2, end));
				if (substitute != null) {
					boolean isTables = substitute instanceof Tables;
					String replace = isTables ? "" : Fixture.escape(substitute.toString());
					result = result.substring(0, pos) + replace + result.substring(end + 1);
					if (isTables)
						tables.addTables((Tables) substitute);
					pos = 0;
				} else
					pos += 2;
			}
		}
		return new Pair<String, Tables>(result, tables);
	}

	// @Override
	public void put(String key, Object value) {
		map.put(key, value);
	}

	public void putAll(Properties properties) {
		map.putAll(properties);
	}

	// @Override
	public void clearAll() {
		map.clear();
	}

	// @Override
	public Object get(String key) {
		return map.get(key);
	}

	// @Override
	public String getAsString(String key) {
		Object result = get(key);
		if (result == null)
			return null;
		return result.toString();
	}

	// @Override
	public Map<Object, Object> getMap() {
		return new HashMap<Object, Object>(map);
	}

	// @Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ArrayList<Object> keys = new ArrayList<Object>(map.keySet());
		Collections.sort(keys, new Comparator<Object>() {
			// @Override
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
		for (Object key : keys) {
			s.append(key);
			s.append(" = ");
			s.append(map.get(key));
			s.append("<br/>");
		}
		return s.toString();
	}
}
