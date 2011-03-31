/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import java.util.HashMap;
import java.util.Map;

public class Owing {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getAmountsOwing() {
		HashMap map = new HashMap();
		map.put("anmol", new Double(5.00));
		map.put("sally", new Double(15.00));
		map.put("ryan", new Double(200.00));
		return map;
	}
}
