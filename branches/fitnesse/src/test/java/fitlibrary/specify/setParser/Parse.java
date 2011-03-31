/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.setParser;

import java.util.Set;

import fitlibrary.specify.eg.Count;

@SuppressWarnings("rawtypes")
public class Parse {
	public Set givenSet(Set set) {
		return set;
	}
	public Set givenColourSet(Set set) {
		return set;
	}
	public Count count(int count) {
		return new Count(count);
	}
}
