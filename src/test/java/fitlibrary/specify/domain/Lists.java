/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 6/09/2006
*/

package fitlibrary.specify.domain;

import java.util.Arrays;
import java.util.List;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

@SuppressWarnings("rawtypes")
public class Lists implements DomainFixtured {
	private List aColourList = Arrays.asList(new Colour[]{ 
			Colour.RED, Colour.GREEN });
	
	public List getAColourList() {
		return aColourList;
	}
	public void setAColourList(List colourList) {
		aColourList = colourList;
	}
	public Colour colour(String name) {
		return new Colour(name);
	}
}
