/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;
import fit.RowFixture;

public class EmptyRowFixture extends RowFixture {

	@Override
	public Object[] query() throws Exception {
		return new Object[] {};
	}
	@Override
	public Class<?> getTargetClass() {
		return java.awt.Point.class;
	}
}
