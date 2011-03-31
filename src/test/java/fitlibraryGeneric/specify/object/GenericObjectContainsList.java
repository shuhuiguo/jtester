/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import java.util.List;

import fitlibrary.object.DomainFixtured;
import fitlibrary.specify.eg.Colour;

public class GenericObjectContainsList implements DomainFixtured {
	private Gen<Colour> gen = new Gen<Colour>();

	public Gen<Colour> getGen() {
		return gen;
	}

	public void setGen(Gen<Colour> gen) {
		this.gen = gen;
	}

	public static class Gen<T> {
		private List<T> ts;

		public List<T> getTs() {
			return ts;
		}

		public void setTs(List<T> ts) {
			this.ts = ts;
		}
	}
}
