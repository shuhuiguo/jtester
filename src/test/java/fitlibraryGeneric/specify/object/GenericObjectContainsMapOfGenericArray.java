/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import java.util.Map;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class GenericObjectContainsMapOfGenericArray implements DomainFixtured {
	private Gen<Integer, Colour> gen = new Gen<Integer, Colour>();

	public Gen<Integer, Colour> getGen() {
		return gen;
	}

	public void setGen(Gen<Integer, Colour> gen) {
		this.gen = gen;
	}

	public static class Gen<S, T> {
		private Map<S, T[]> ts;

		public Map<S, T[]> getTs() {
			return ts;
		}

		public void setTs(Map<S, T[]> ts) {
			this.ts = ts;
		}
	}
}
