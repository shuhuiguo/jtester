/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.PrimitiveArrayFixture;

public class PrimitiveArrayFixtureUnderTestMixed extends PrimitiveArrayFixture {

    public PrimitiveArrayFixtureUnderTestMixed() {
        super(new Object[] { new Integer(1), new Double(2.0), "three" });
    }

}
