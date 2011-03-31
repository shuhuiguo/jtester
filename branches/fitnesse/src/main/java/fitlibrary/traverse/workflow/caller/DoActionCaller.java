/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import java.lang.reflect.InvocationTargetException;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.closure.LookupMethodTarget;
import fitlibrary.exception.AbandonException;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.AbstractDoCaller;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class DoActionCaller extends AbstractDoCaller {
	private ICalledMethodTarget target;
	private String methodName;

	public DoActionCaller(Row row, Evaluator evaluator, boolean sequencing, LookupMethodTarget lookupTarget) {
		methodName = row.methodNameForCamel(evaluator.getRuntimeContext());
		try {
			target = lookupTarget.findMethodByArity(row, 0, row.size(), !sequencing, evaluator);
		} catch (Exception e) {
			setProblem(e);
		}
	}

	// @Override
	public boolean isValid() { // This has to be the last one, which is always
								// run
		return target != null;
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults) throws Exception {
		try {
			TypedObject typedResult = target.invokeTyped(row.fromAt(1), testResults);
			Object result = null;
			if (typedResult != null)
				result = typedResult.getSubject();
			if (result instanceof Boolean)
				target.color(row, ((Boolean) result).booleanValue(), testResults);
			return typedResult;
		} catch (AbandonException e) {
			return new GenericTypedObject(null);
		} catch (InvocationTargetException e) {
			Throwable throwable = PlugBoard.exceptionHandling.unwrapThrowable(e);
			if (throwable instanceof FitLibraryShowException)
				row.at(0).error(testResults);
			throw e;
		}
	}

	// @Override
	public String ambiguityErrorMessage() {
		return methodName + "() in " + target.getOwningClass().getName();
	}
}
