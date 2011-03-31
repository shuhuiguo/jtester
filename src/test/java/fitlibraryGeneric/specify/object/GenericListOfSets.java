/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import java.util.List;
import java.util.Set;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class GenericListOfSets implements DomainFixtured {
	private Gen<Colour> gen = new Gen<Colour>();

	public Gen<Colour> getGen() {
		return gen;
	}

	public void setGen(Gen<Colour> gen) {
		this.gen = gen;
	}

	public static class Gen<T> {
		private List<Set<T>> ts;

		public List<Set<T>> getTs() {
			return ts;
		}

		public void setTs(List<Set<T>> ts) {
			this.ts = ts;
		}
	}
}
