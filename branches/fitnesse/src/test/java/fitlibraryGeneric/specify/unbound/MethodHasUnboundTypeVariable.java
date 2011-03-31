/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 11/11/2006
 */

package fitlibraryGeneric.specify.unbound;

import fitlibrary.object.DomainFixtured;

public class MethodHasUnboundTypeVariable implements DomainFixtured {
	public <T extends Object> T same(T t) {
		return t;
	}
}
