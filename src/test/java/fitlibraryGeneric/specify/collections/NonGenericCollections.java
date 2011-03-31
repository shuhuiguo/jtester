/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibraryGeneric.specify.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

@SuppressWarnings("rawtypes")
public class NonGenericCollections implements DomainFixtured {
	private List aList;
	private Set aSet;
	private Map aMap;

	public List getAList() {
		return aList;
	}

	public void setAList(List list) {
		aList = list;
	}

	public Set getASet() {
		return aSet;
	}

	public void setASet(Set set) {
		aSet = set;
	}

	public Colour colour(Colour colour) {
		return colour;
	}

	public Map getAMap() {
		return aMap;
	}

	public void setAMap(Map map) {
		aMap = map;
	}
}
