/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.mapParser;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Match {
	@SuppressWarnings("unchecked")
	public Map getMapOfStringAbc() {
		Map map = new HashMap();
		map.put("a","b");
		map.put("b","c");
		map.put("c","a");
		return map;
	}
	@SuppressWarnings("unchecked")
	public Map getMapOf123() {
		Map map = new HashMap();
		map.put(new Integer(1),new Integer(2));
		map.put(new Integer(2),new Integer(3));
		map.put(new Integer(3),new Integer(4));
		return map;
	}
	public Map getMapEmpty() {
		return new HashMap();
	}
}
