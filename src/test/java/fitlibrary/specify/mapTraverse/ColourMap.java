/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.mapTraverse;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class ColourMap implements DomainFixtured {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getColourMap() {
		HashMap map = new HashMap();
		map.put(Colour.RED,Colour.GREEN);
		map.put(Colour.YELLOW,Colour.BLUE);
		return map;
	}
}
