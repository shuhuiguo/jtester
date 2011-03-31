/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.ConstraintFixture;

public class SucceedConstraint extends ConstraintFixture {
	public boolean aB(int a, int b) {
		return a < b;
	}
	public int bC(int b, int c) {
		return b + c;
	}
}
