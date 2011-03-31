/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class SetFixtureUnderTest2 extends fitlibrary.SetFixture {
	public SetFixtureUnderTest2() throws Exception {
		super(new Object[]{
    			new MockCollection(1,"one"),
				new MockCollection(1,"two"),
				new MockCollection(1,"two"),
				new Some()});
	}
	public static class Some {
		public String getSome() {
			return "one";
		}
	}
}
