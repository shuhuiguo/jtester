/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.DoFixture;

public class DoFixtureTearDown extends DoFixture {
	public void tearDown() {
        throw new RuntimeException("TearDown Worked.");
    }
    public void anException() {
        throw new RuntimeException("ex");
    }
}
