/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 4/11/2006
*/

package fitlibrary.specify.calculate;

import fitlibrary.specify.eg.Colour;

public class ResultingObjectIsSubType {
	public Object superclassColour(Colour colour) {
		return colour;
	}
	@SuppressWarnings("rawtypes")
	public Comparable interfaceColour(Colour colour) {
		return colour;
	}
}
