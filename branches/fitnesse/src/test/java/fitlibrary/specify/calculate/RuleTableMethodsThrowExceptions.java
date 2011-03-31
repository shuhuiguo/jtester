/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.calculate;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.traverse.function.RuleTable;

public class RuleTableMethodsThrowExceptions extends RuleTable {

	public RuleTableMethodsThrowExceptions() {
		super(new Sut());
	}
	
	public static class Sut {
		private boolean inThrowsException = false;
		private boolean outThrowsException = false;
		private boolean resetThrowsException = false;
		private boolean executeThrowsException = false;
		private int in = 0;
		
		public void setInThrowsException(boolean inThrowsException) {
			this.inThrowsException = inThrowsException;
		}
		public void setOutThrowsException(boolean outThrowsException) {
			this.outThrowsException = outThrowsException;
		}
		public void setResetThrowsException(boolean resetThrowsException) {
			this.resetThrowsException = resetThrowsException;
		}
		public void setExecuteThrowsException(boolean executeThrowsException) { 
			this.executeThrowsException  = executeThrowsException;
		}
		public void setIn(int in) {
			if (inThrowsException) {
				inThrowsException = false;
				throw new FitLibraryException("in exception");
			}
			this.in  = in;
		}
		public int getOut() {
			if (outThrowsException) {
				outThrowsException = false;
				throw new FitLibraryException("out exception");
			}
			return in;
		}
		public void reset() {
			if (resetThrowsException) {
				resetThrowsException = false;
				throw new FitLibraryException("reset exception");
			}
		}
		public void execute() {
			if (executeThrowsException) {
				executeThrowsException = false;
				throw new FitLibraryException("execute exception");
			}
		}
	}

}
