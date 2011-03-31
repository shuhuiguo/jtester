/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fit.specify;

import fit.ColumnFixture;

public class ColumnFixtureUnderTestWithArgs extends ColumnFixture {
    public int third = 0;
    
    public int sum() {
        return Integer.parseInt(getArgs()[0]) + 
            Integer.parseInt(getArgs()[1]) + 
            third;
    }
}
