/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fit.specify;

public class RowFixtureUnderTest2 extends fit.RowFixture {

	@Override
	public Object[] query() throws Exception {
		return new Object[0];
	}
	@Override
	public Class<?> getTargetClass() {
		return RowFixtureUnderTest.MockClass.class;
	}
}
