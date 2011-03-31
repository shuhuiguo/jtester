/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.function.FunctionTraverse;

public abstract class FunctionFixture extends FitLibraryFixture {
	private FunctionTraverse functionTraverse;

	public FunctionFixture() {
		//
	}

	public FunctionFixture(Object sut) {
		setSystemUnderTest(sut);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		return functionTraverse.interpretWithSetUp(table, testResults);
	}

	protected void setTraverse(FunctionTraverse functionEmu) {
		super.setTraverse(functionEmu);
		this.functionTraverse = functionEmu;
	}

	/**
	 * Defines the String that signifies that the value in the row above is to
	 * be used again. Eg, it could be set to "" or to '"".
	 */
	public void setRepeatString(String repeat) {
		functionTraverse.setRepeatString(repeat);
	}

	/**
	 * Defines the String that signifies that no result is expected; instead an
	 * exception is.
	 */
	public void setExceptionString(String exceptionString) {
		functionTraverse.setExceptionString(exceptionString);
	}
}
