/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.CombinationFixture;
import fitlibrary.DoFixture;

public class TimesCombinationSetUp extends DoFixture {
	public Combine combine() {
		return new Combine();
	}
	public static class Combine extends CombinationFixture {
		private boolean isSetUp = false;
		
		public void setUp() {
			isSetUp = true;
		}
		public void tearDown() {
			throw new RuntimeException("tear down");
		}
		public int combine(int x, int y) {
			if (!isSetUp)
				throw new RuntimeException("Not set up");
			return x * y;
		}
	}
}
