/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.function.CalculateTraverse;

public class CalculateFixture extends FunctionFixture {
	public CalculateFixture() {
		CalculateTraverse calculateTraverse = new CalculateTraverse(this);
		setTraverse(calculateTraverse);
	}

	public CalculateFixture(Object sut) {
		this();
		setSystemUnderTest(sut);
	}
}
