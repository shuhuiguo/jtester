/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.missingMethod;

public class ShowInJustSut {
	public Colour colour() {
		return new Colour("red");
	}

	public static class Colour {
		public Colour(String name) {
			//
		}
		// an Entity has no static parse() method
		// Note that it also doesn't have an equals() method
	}

}
