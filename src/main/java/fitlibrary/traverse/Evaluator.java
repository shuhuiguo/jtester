/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.flow.IScope;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Table;
import fitlibrary.typed.TypedObject;

public interface Evaluator extends RuntimeContextual, VariableResolver {
	Object interpretAfterFirstRow(Table table, TestResults testResults);

	TypedObject getTypedSystemUnderTest();

	RuntimeContextInternal getRuntimeContext();

	void setDynamicVariable(String key, Object value);

	IScope getScope();
}