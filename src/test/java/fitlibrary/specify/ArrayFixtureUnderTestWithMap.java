/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayFixtureUnderTestWithMap  {
	public List<Map<String,Object>> map() throws Exception {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        result.add(makeMap(1, "one"));
        result.add(makeMap(1, "two"));
        result.add(makeMap(2, "two"));
        return result;
   }
	private Map<String,Object> makeMap(int plus, String ampersand) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("plus",plus);
        map.put("&",ampersand);
        return map;
    }
}
