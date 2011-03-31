/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fitlibrary.SetFixture;
import fitlibrary.SubsetFixture;

public class MapFixture {
	public SetFixture getMap() {
        HashMap<String,String> theMap = new HashMap<String,String>();
        theMap.put("a", "b");     
        theMap.put("c", "d");     
        return new SetFixture(theMap);
    }
    public SubsetFixture getSubsetMap() {
        HashMap<String,String> theMap = new HashMap<String,String>();
        theMap.put("a", "b");     
        theMap.put("c", "d");     
        return new SubsetFixture(theMap);
    }
	public Set<Map<String,Object>> camelFreeMap() {
		Set<Map<String,Object>> result = new HashSet<Map<String,Object>>();
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
