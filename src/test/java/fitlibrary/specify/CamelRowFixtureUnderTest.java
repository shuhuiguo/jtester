/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class CamelRowFixtureUnderTest extends fit.RowFixture {
    @Override
	public Object[] query() throws Exception {
         return new MockCollection[]{
    			new MockCollection(1,"one"),
				new MockCollection(1,"two"),
				new MockCollection(2,"two")};
    }
	@Override
	public Class<?> getTargetClass() {
        return MockCollection.class;
    }

}
