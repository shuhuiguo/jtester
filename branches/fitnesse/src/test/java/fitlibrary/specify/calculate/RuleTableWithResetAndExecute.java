/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.calculate;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.traverse.function.RuleTable;

public class RuleTableWithResetAndExecute extends RuleTable {

	public RuleTableWithResetAndExecute() {
		super(new Sut());
	}
	
	public static class Sut {
		private int executes, resets;
		
		public void setExpectedResets(int i) {
			if (i != resets)
				throw new FitLibraryException("Was: "+resets);
		}
		public void setExpectedExecutes(int i) {
			if (i != executes)
				throw new FitLibraryException("Was: "+executes);
		}
		public int getResets() {
			return resets;
		}
		public int getExecutes() {
			return executes;
		}
		public void reset() {
			resets += 1;
		}
		public void execute() {
			executes += 1;
		}
	}

}
