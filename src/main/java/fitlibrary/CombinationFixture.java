/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.function.CombinationTraverse;

public class CombinationFixture extends FunctionFixture {
	public CombinationFixture() {
		CombinationTraverse combinationTraverse = new CombinationTraverse(this);
		setTraverse(combinationTraverse);
	}

	public CombinationFixture(Object sut) {
		this();
		setSystemUnderTest(sut);
	}
}
