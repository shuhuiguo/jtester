/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 16/11/2006
 */

package fitlibraryGeneric.specify.workflow;

import fitlibrary.specify.eg.Colour;

public class Check {
	public Gen<Colour> getAGenericColourObject() {
		return new Gen<Colour>(Colour.RED);
	}

	public Gen<Integer> getAGenericIntegerObject() {
		return new Gen<Integer>(23);
	}

	public static class Gen<T> {
		T t;

		public Gen(T t) {
			this.t = t;
		}

		public T getT() {
			return t;
		}
	}
}
