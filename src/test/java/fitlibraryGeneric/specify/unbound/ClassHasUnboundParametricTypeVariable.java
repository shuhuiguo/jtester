/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 11/11/2006
 */

package fitlibraryGeneric.specify.unbound;

import java.util.List;

import fitlibrary.object.DomainFixtured;

public class ClassHasUnboundParametricTypeVariable<T> implements DomainFixtured {
	private List<T> t;

	public List<T> getT() {
		return t;
	}

	public void setT(List<T> t) {
		this.t = t;
	}
}
