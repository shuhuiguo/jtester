/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fit.Parse;

/**
 * Clear the counts for the inner fixtures to test fit.Summary.
 */
public class ResetCounts extends fit.Fixture {
	@Override
	public void doTable(Parse table) {
		counts.right = 0;
		counts.wrong = 0;
		counts.exceptions = 0;
		counts.ignores = 0;
	}
}
