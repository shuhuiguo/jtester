/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow.caller;

import java.lang.reflect.InvocationTargetException;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.closure.LookupMethodTarget;
import fitlibrary.exception.method.MissingMethodException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.AbstractDoCaller;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class SpecialCaller extends AbstractDoCaller {
	private String methodName;
	private ICalledMethodTarget specialMethod;
	private TwoStageSpecial twoStageSpecial = null;

	public SpecialCaller(Row row, Evaluator evaluator, LookupMethodTarget lookupTarget) {
		methodName = row.text(0, evaluator);
		specialMethod = lookupTarget.findSpecialMethod(evaluator, methodName);
		if (specialMethod != null && TwoStageSpecial.class.isAssignableFrom(specialMethod.getReturnType())) {
			try {
				twoStageSpecial = (TwoStageSpecial) specialMethod.invoke(new Object[] { row });
			} catch (InvocationTargetException e) {
				specialMethod = null;
				if (e.getCause() instanceof Exception)
					setProblem((Exception) e.getCause());
			} catch (MissingMethodException e) {
				specialMethod = null;
				setProblem(e);
			} catch (Exception e) {
				specialMethod = null;
			}
		}
	}

	// @Override
	public boolean isValid() {
		return specialMethod != null;
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults) throws Exception {
		if (twoStageSpecial != null) {
			twoStageSpecial.run(testResults);
			return new GenericTypedObject(null);
		}
		return new GenericTypedObject(specialMethod.invoke(new Object[] { row, testResults }));
	}

	// @Override
	public String ambiguityErrorMessage() {
		return "Special " + methodName + "(Row,TestResults) in " + specialMethod.getOwningClass().getName();
	}
}
