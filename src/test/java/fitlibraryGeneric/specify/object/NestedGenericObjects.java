/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class NestedGenericObjects implements DomainFixtured {
	private Gen<Colour> gen = new Gen<Colour>();

	public Gen<Colour> getGen() {
		return gen;
	}

	public void setGen(Gen<Colour> gen) {
		this.gen = gen;
	}

	public static class Gen<T> {
		private Gen2<T> t;

		public Gen2<T> getT() {
			return t;
		}

		public void setT(Gen2<T> t) {
			this.t = t;
		}
	}

	public static class Gen2<V> {
		private V v;

		public V getV() {
			return v;
		}

		public void setV(V t) {
			this.v = t;
		}
	}
}
