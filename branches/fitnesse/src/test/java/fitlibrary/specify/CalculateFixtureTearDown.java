/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.CalculateFixture;

public class CalculateFixtureTearDown extends CalculateFixture {
	public void tearDown() {
        throw new RuntimeException("TearDown Worked.");
    }
    public int resultA(int a) { 
        throw new RuntimeException("ex"+a);
    }
}
