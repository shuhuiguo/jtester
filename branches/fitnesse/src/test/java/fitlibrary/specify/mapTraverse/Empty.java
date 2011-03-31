/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.mapTraverse;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.object.DomainFixtured;

public class Empty implements DomainFixtured {
	@SuppressWarnings({ "rawtypes" })
	public Map getEmptyMap() {
		return new HashMap();
	}
}
