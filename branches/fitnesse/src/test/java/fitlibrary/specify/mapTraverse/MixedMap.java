/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.mapTraverse;

import java.util.Map;
import java.util.TreeMap;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;
import fitlibrary.specify.eg.Count;

public class MixedMap implements DomainFixtured {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getMixedMap() {
		Map map = new TreeMap();
		// The Comparator assures that the Colour comes first
		map.put(Colour.RED, Colour.GREEN);
		map.put(new Count(1), new Count(2));
		return map;
	}
}
