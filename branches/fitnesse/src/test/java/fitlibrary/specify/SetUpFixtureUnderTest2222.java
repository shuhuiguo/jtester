/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import fitlibrary.SetUpFixture;

public class SetUpFixtureUnderTest2222 extends SetUpFixture {
	private boolean setup = false;

	public void setUp() {
		setup = true;
	}

	public void aPercent(int a, int b) {
		if (!setup)
			throw new RuntimeException("not setup");
	}

	public void tearDown() {
		throw new RuntimeException("teardown");
	}

	public SetUpFixtureUnderTest2222 doNotDoAgain() {
		return this;
	}

	public SetUpFixtureUnderTest2222 doAgain() {
		return new SetUpFixtureUnderTest2222();
	}
}
