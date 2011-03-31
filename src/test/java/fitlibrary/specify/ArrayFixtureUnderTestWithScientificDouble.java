/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fit.ScientificDouble;
import fitlibrary.ArrayFixture;

public class ArrayFixtureUnderTestWithScientificDouble extends ArrayFixture {
	public ArrayFixtureUnderTestWithScientificDouble() throws Exception {
		super(new Object[]{
    			new Pair(1.11,2.22),
				new Pair(1.11,2.22),
				new Pair(1.11,2.22)
				});
	}
	public static class Pair {
		private ScientificDouble one, two;
		
		public Pair(double i, double j) {
			this.one = new ScientificDouble(i);
			this.two = new ScientificDouble(j);
		}
		public ScientificDouble getOne() {
			return one;
		}
		public ScientificDouble getTwo() {
			return two;
		}
	}

}
