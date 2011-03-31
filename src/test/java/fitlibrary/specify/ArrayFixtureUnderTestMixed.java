/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayFixtureUnderTestMixed extends fitlibrary.ArrayFixture {
	public ArrayFixtureUnderTestMixed() throws Exception {
	    setActualCollection(query());
	}
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List query() throws Exception {
        List result = new ArrayList();
        result.add(makeMap(new Integer(1), "one"));
        result.add(new MockCollection(1,"two"));
        result.add(makeMap(new Integer(2), "two"));
        return result;
   }
    private Map<String,Object> makeMap(Integer plus, String ampersand) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("plus",plus);
        map.put("ampersand",ampersand);
        return map;
    }
}
