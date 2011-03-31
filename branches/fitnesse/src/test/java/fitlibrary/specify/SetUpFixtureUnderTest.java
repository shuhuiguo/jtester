/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import fitlibrary.SetUpFixture;

public class SetUpFixtureUnderTest extends SetUpFixture {
	public void aB(int a, int b) {
		if (a < 0)
			throw new RuntimeException();
	}
}
