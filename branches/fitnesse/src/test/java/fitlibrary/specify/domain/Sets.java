/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 6/09/2006
*/

package fitlibrary.specify.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

@SuppressWarnings({"unchecked","rawtypes"})
public class Sets implements DomainFixtured {
	private Set aSetOfColours = new HashSet(Arrays.asList(new Colour[]{
			Colour.RED, Colour.GREEN }));

	public Set getASetOfColours() {
		return aSetOfColours;
	}
	public void setASetOfColours(Set setOfColours) {
		aSetOfColours = setOfColours;
	}
	public Colour colour(String name) {
		return new Colour(name);
	}
}
