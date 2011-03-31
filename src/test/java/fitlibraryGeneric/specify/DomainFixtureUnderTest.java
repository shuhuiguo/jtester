/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibraryGeneric.specify;

import fitlibrary.DomainFixture;
import fitlibraryGeneric.specify.PrePopulated.Colour;

public class DomainFixtureUnderTest extends DomainFixture {
    public DomainFixtureUnderTest() {
        super(new PrePopulated());
    }
    public Colour colourCount(String colour, int count) {
    	Colour colour2 = new Colour(colour);
    	colour2.setCount(count);
		return colour2;
    }
    public Class<?> concreteClassOfInterfaceType(String typeName) {
    	if ("concrete one".equals(typeName))
    		return ConcreteClassOne.class;
    	return null;
    }
}
