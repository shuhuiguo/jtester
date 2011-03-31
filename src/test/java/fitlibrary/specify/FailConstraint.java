/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.ConstraintFixture;

public class FailConstraint extends ConstraintFixture {
	public FailConstraint() {
		super(false);
	}
	public boolean bA(int b, int a) {
		return a < b;
	}
}
