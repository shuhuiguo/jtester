/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.CalculateFixture;

public class CalculateFixtureSetUp extends CalculateFixture {
    private boolean isSetUp = false;
    
	public void setUp() {
        isSetUp = true;
    }
    public int resultA(int a) { 
        if (isSetUp)
            return a+1;
        return 0;
    }
}
