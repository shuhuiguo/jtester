/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.traverse.function.ConstraintTraverse;

/**
 * Similar to CalculateFixture, except that there is an implied expected boolean
 * result of true.
 * 
 * See the SpecifyFixture specifications for examples
 */

public class ConstraintFixture extends FunctionFixture {
	private ConstraintTraverse constraintTraverse = new ConstraintTraverse(this);

	public ConstraintFixture() {
		setTraverse(constraintTraverse);
	}

	public ConstraintFixture(Object sut) {
		this();
		setSystemUnderTest(sut);
	}

	public ConstraintFixture(boolean expected) {
		this();
		constraintTraverse.setExpected(expected);
	}

	public ConstraintFixture(Object sut, boolean expected) {
		this(sut);
		constraintTraverse.setExpected(expected);
	}
}
