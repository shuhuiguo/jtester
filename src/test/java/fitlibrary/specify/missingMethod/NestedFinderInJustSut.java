/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.missingMethod;

import java.util.List;

@SuppressWarnings({ "rawtypes" })
public class NestedFinderInJustSut {
	public void addToColourMix(List colours) {
		//
	}

	public Mix colourQuantity(Colour colour, int quantity) {
		return new Mix(colour, quantity);
	}

	public static class Colour {
		// an Entity as no static parse() method
	}

	public static class Mix {
		public Mix(Colour colour, int quantity) {
			//
		}
	}
}
