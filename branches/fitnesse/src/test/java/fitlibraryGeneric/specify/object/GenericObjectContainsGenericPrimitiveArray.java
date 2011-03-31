/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.specify.object;

import fitlibrary.object.DomainFixtured;

public class GenericObjectContainsGenericPrimitiveArray implements DomainFixtured {
	private Gen<Integer> gen = new Gen<Integer>();

	public Gen<Integer> getGen() {
		return gen;
	}

	public void setGen(Gen<Integer> gen) {
		this.gen = gen;
	}

	public static class Gen<T> {
		private T[] ts;

		public T[] getTs() {
			return ts;
		}

		public void setTs(T[] ts) {
			this.ts = ts;
		}
	}
}
