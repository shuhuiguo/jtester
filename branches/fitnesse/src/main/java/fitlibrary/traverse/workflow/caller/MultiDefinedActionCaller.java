/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import fitlibrary.definedAction.ParameterBinder;
import fitlibrary.global.TemporaryPlugBoardForRuntime;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.traverse.workflow.AbstractDoCaller;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class MultiDefinedActionCaller extends AbstractDoCaller {
	private final RuntimeContextInternal runtime;
	private final String methodName;
	private final ParameterBinder multiParameterSubstitution;
	private final boolean furtherRows;

	public MultiDefinedActionCaller(Row row, RuntimeContextInternal runtime) {
		this.runtime = runtime;
		this.furtherRows = runtime.hasRowsAfter(row);
		methodName = row.text(0, runtime.getResolver());
		multiParameterSubstitution = TemporaryPlugBoardForRuntime.definedActionsRepository().lookupMulti(methodName);
	}

	// @Override
	public String ambiguityErrorMessage() {
		return "multi defined action " + methodName;
	}

	// @Override
	public boolean isValid() {
		return furtherRows && multiParameterSubstitution != null;
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults) throws Exception {
		return new GenericTypedObject(new MultiDefinedActionRunnerTraverse(multiParameterSubstitution, runtime));
	}
}
