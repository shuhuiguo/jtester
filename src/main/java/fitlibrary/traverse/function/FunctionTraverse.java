/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.function;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;

public abstract class FunctionTraverse extends Traverse {
	protected String repeatString = null;
	protected String exceptionString = null;

	public FunctionTraverse() {
		// No SUT
	}

	public FunctionTraverse(Object sut) {
		super(sut);
	}

	/**
	 * Defines the String that signifies that the value in the row above is to
	 * be used again. Eg, it could be set to "" or to '"".
	 */
	public void setRepeatString(String repeat) {
		this.repeatString = repeat;
	}

	/**
	 * Defines the String that signifies that no result is expected; instead an
	 * exception is.
	 */
	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}

	public Object interpretWithSetUp(Table table, TestResults testResults) {
		Object result = null;
		try {
			result = interpretAfterFirstRow(table, testResults);
		} catch (Exception e) {
			table.at(0).error(testResults, e);
		}
		return result;
	}
}
