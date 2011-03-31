/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import fitlibrary.CombinationFixture;

public class MixedCombination extends CombinationFixture {
	public boolean combine(String s, int x) {
		return x == 1;
	}
}
