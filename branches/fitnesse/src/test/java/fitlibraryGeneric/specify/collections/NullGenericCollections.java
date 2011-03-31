/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/11/2006
 */

package fitlibraryGeneric.specify.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class NullGenericCollections implements DomainFixtured {
	public List<Colour> getAList() {
		return null;
	}

	public List<List<Colour>> getAListOfLists() {
		return null;
	}

	public Set<Colour> getASet() {
		return null;
	}

	public Set<Set<Colour>> getASetOfSets() {
		return null;
	}

	public Map<Colour, Colour> getAMap() {
		return null;
	}

	public Colour[] getAnArray() {
		return null;
	}
}
