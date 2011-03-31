/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import java.util.Map;

import fitlibrary.object.DomainFixtured;

public class GenericObjectContainsMap implements DomainFixtured {
	private Gen<Boolean, Integer> gen = new Gen<Boolean, Integer>();

	public Gen<Boolean, Integer> getGen() {
		return gen;
	}

	public void setGen(Gen<Boolean, Integer> gen) {
		this.gen = gen;
	}

	public static class Gen<S, T> {
		private Map<S, T> fun;

		public Map<S, T> getFun() {
			return fun;
		}

		public void setFun(Map<S, T> ts) {
			this.fun = ts;
		}
	}
}
