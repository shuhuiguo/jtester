/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 16/11/2006
 */

package fitlibraryGeneric.specify.workflow;

import fitlibrary.specify.eg.Colour;

public class GenericObjectFollows {
	public Gen<Colour> aGenericColourObject() {
		return new Gen<Colour>();
	}

	public Gen<Integer> aGenericIntegerObject() {
		return new Gen<Integer>();
	}

	public static class Gen<T> {
		public T identity(T t) {
			return t;
		}
	}
}
