/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
*/

package fitlibrary.specify.missingProperty;

import fitlibrary.object.DomainFixtured;

public class InJustSut implements DomainFixtured {
	Colour colour = new Colour();
	
	public Colour getColour() {
		return colour;
	}
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	public static class Colour {
		//
	}
}
