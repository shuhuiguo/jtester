/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/11/2006
 */

package fitlibraryGeneric.specify.collections;

import java.util.List;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class GenericCollectionsUseObjectFactory implements DomainFixtured {
	private List<Colour> aList;
	private Set<Colour> aSet;

	public List<Colour> getAList() {
		return aList;
	}

	public void setAList(List<Colour> list) {
		aList = list;
	}

	public Set<Colour> getASet() {
		return aSet;
	}

	public void setASet(Set<Colour> set) {
		aSet = set;
	}

	public Colour colourRenamed(Colour colour) {
		return colour;
	}
}
