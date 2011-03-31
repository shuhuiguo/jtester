/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class SubsetFixtureUnderTest extends fitlibrary.SubsetFixture {
	public SubsetFixtureUnderTest() throws Exception {
		super(new CamelRowFixtureUnderTest().query());
	}
}
