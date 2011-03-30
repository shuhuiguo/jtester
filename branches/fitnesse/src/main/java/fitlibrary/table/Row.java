/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.table;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;

public interface Row extends TableElement<Row, Cell> {
	Cell addCell();

	Cell addCell(String s);

	Cell addCell(String text, int cols);

	String text(int i, VariableResolver resolver);

	void pass(TestResults testResults);

	void passKeywords(TestResults testResults);

	void fail(TestResults testResults);

	// @Override
	void error(TestResults testResults, Throwable e);

	void ignore(TestResults testResults);

	void missing(TestResults testResults);

	void shown();

	int argumentCount();

	String methodNameForCamel(RuntimeContextInternal runtime);

	String methodNameForPlain(RuntimeContextInternal runtime);

	int getColumnSpan();

	void setColumnSpan(int span);

	void setIsHidden();

	boolean didPass();

	boolean didFail();

	void removeElementAt(int i);

	void clear();
}
