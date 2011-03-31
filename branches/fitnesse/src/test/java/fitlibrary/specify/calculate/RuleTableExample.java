/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.calculate;

import fitlibrary.traverse.function.RuleTable;

public class RuleTableExample extends RuleTable {

	public RuleTableExample() {
		super(new Sut());
	}
	
	public static class Sut {
		private int in, in2;
		
		public int getOut() {
			return in + in2;
		}
		public void setIn(int i) {
			this.in = i;
		}
		public void setIn2(int i) {
			this.in2 = i;
		}
	}
}
