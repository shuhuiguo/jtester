/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.DoFixture;

public class DoFixtureSetUpWithException extends DoFixture {
	public void setUp() {
        throw new RuntimeException("setUp exception.");
    }
}
