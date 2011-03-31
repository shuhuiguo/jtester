/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 25/08/2006
*/

package fitlibrary.specify.calculate;

import fitlibrary.parser.tree.ListTree;

public class WithLists {
	public ListTree plus12(ListTree t1, ListTree t2) {
		return new ListTree("", new ListTree[]{ t1, t2 });
	}
}
