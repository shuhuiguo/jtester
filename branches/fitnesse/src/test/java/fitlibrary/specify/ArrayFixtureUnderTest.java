/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class ArrayFixtureUnderTest extends fitlibrary.ArrayFixture {
	public ArrayFixtureUnderTest() throws Exception {
		super(new CamelRowFixtureUnderTest().query());
	}
}
