/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;

public class ClassMethodTarget implements MethodTarget {
	private Class<?> componentType;
	private Evaluator evaluator;
	private Class<?> actualType;

	public ClassMethodTarget(Class<?> componentType, Evaluator evaluator, TypedObject typedObject) {
		this.componentType = componentType;
		this.evaluator = evaluator;
		this.actualType = typedObject.classType();
	}

	// @Override
	public void setTypedSubject(TypedObject typedObject) {
		//
	}

	// @Override
	public String getResult() throws Exception {
		return actualType.getName();
	}

	// @Override
	public boolean matches(Cell expectedCell, TestResults testResults) {
		try {
			Class<?> expectedType = PlugBoard.lookupTarget.findClassFromFactoryMethod(evaluator, componentType,
					expectedCell.text(evaluator));
			return actualType == expectedType;
		} catch (Exception e) {
			expectedCell.error(testResults, e);
			return false;
		}
	}

	// @Override
	public boolean invokeAndCheckCell(Cell expectedCell, boolean matchedAlready, TestResults testResults) {
		try {
			if (matches(expectedCell, testResults)) {
				if (matchedAlready)
					expectedCell.pass(testResults);
				return true;
			} else if (matchedAlready)
				expectedCell.fail(testResults, getResult(), evaluator);
		} catch (Exception e) {
			expectedCell.error(testResults, e);
		}
		return false;
	}

	public String getMethodClass() {
		return "ClassMethodTarget";
	}
}
