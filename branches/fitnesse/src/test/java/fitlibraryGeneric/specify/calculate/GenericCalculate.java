/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 4/11/2006
*/

package fitlibraryGeneric.specify.calculate;

import fitlibrary.specify.eg.Colour;

public class GenericCalculate {
	public Gen<Colour> getGen() {
		return new Gen<Colour>();
	}
	public static class Gen <T> {
		public T identity(T t) {
			return t;
		}
	}
}
