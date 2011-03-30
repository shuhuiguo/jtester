/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.special;

import java.lang.reflect.Method;

import fitlibrary.closure.CalledMethodTarget;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.closure.LookupMethodTarget;
import fitlibrary.closure.MethodClosure;
import fitlibrary.global.PlugBoard;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

public class PositionedTargetWasFound implements PositionedTarget {
	private final int innerFrom;
	private final int innerUpTo;
	private final boolean sequencing;
	private String specialName;
	private ICalledMethodTarget innerTarget;
	private final ICalledMethodTarget specialTarget;
	private String innerName = "";
	private String innerTargetMissingMessage = "";

	public PositionedTargetWasFound(Evaluator evaluator, String[] cells, TypedObject typedObject, Method method,
			int innerFrom, int innerUpTo, boolean sequencing, LookupMethodTarget lookupTarget) {
		this.innerFrom = innerFrom;
		this.innerUpTo = innerUpTo;
		this.sequencing = sequencing;
		specialName = method.getName();
		specialTarget = new CalledMethodTarget(new MethodClosure(typedObject, method), evaluator);
		int argCount = 0;
		argCount = determineInnerSignature(cells, evaluator.getRuntimeContext());
		try {
			innerTarget = lookupTarget.findTheMethodMapped(innerName, argCount, evaluator);
		} catch (Exception e) {
			innerTargetMissingMessage = e.getMessage();
		}
	}

	private int determineInnerSignature(String[] cells, RuntimeContextInternal runtime) {
		if (sequencing) {
			innerName = cells[innerFrom];
			return innerUpTo - innerFrom - 1;
		}
		for (int i = innerFrom; i < innerUpTo; i += 2)
			innerName += " " + cells[i];
		innerName = runtime.extendedCamel(innerName);
		return (innerUpTo - innerFrom) / 2;
	}

	// @Override
	public boolean isFound() {
		return specialTarget != null && innerTarget != null;
	}

	// @Override
	public String ambiguityErrorMessage() {
		return "Special " + specialName + "(" + args(specialTarget) + ") + " + innerName + "(" + args(innerTarget)
				+ ") (in " + specialTarget.getOwningClass().getName() + " + " + innerTarget.getOwningClass().getName()
				+ ")";
	}

	// @Override
	public boolean partiallyValid() {
		return specialTarget != null;
	}

	// @Override
	public String getPartialErrorMessage() {
		return innerTargetMissingMessage;
	}

	// @Override
	public TypedObject run(Row row, TestResults testResults, RuntimeContextInternal runtime) {
		try {
			Object[] args = createArguments(new DoActionInContext(innerTarget, row, innerFrom, innerUpTo, sequencing,
					runtime), testResults, row);
			Object result = specialTarget.invoke(args);
			if (result instanceof Boolean)
				if (((Boolean) result).booleanValue())
					row.at(0).pass(testResults);
				else
					row.at(0).fail(testResults);
			return new GenericTypedObject(result);
		} catch (Exception e) {
			if (PlugBoard.exceptionHandling.unwrappedIsShow(e))
				row.at(operatorCell()).error(testResults);
			row.error(testResults, e);
		}
		return null;
	}

	private int operatorCell() {
		if (innerFrom == 0)
			return innerUpTo;
		return 0;
	}

	private String args(ICalledMethodTarget target) {
		Class<?>[] parameterTypes = target.getParameterTypes();
		if (parameterTypes.length == 0)
			return "";
		String s = "";
		for (Class<?> t : parameterTypes)
			s += "," + t.getSimpleName();
		return s.substring(1);
	}

	private Object[] createArguments(DoAction action, TestResults testResults, Row row) throws Exception {
		if (innerFrom == 0)
			return createPostArguments(action, testResults, row);
		return createPreArguments(action, testResults, row);
	}

	private Object[] createPreArguments(DoAction action, TestResults testResults, Row row) throws Exception {
		Parser[] parameterParsers = specialTarget.getParameterParsers();
		Class<?>[] parameterTypes = specialTarget.getParameterTypes();
		Object[] args = new Object[parameterParsers.length];
		for (int i = 0; i < parameterParsers.length - 1; i++) {
			Parser parser;
			if (parameterTypes[i] == Object.class)
				parser = innerTarget.getResultParser();
			else
				parser = parameterParsers[i];
			args[i] = parser.parseTyped(row.at(i * 2 + 1), testResults).getSubject();
		}
		args[parameterParsers.length - 1] = action;
		return args;
	}

	private Object[] createPostArguments(DoAction action, TestResults testResults, Row row) throws Exception {
		Parser[] parameterParsers = specialTarget.getParameterParsers();
		Class<?>[] parameterTypes = specialTarget.getParameterTypes();
		Object[] args = new Object[parameterParsers.length];
		args[0] = action;
		for (int i = 1; i < parameterParsers.length; i++) {
			Parser parser;
			if (parameterTypes[i] == Object.class)
				parser = innerTarget.getResultParser();
			else
				parser = parameterParsers[i];
			args[i] = parser.parseTyped(row.at(innerUpTo + i * 2 - 1), testResults).getSubject();
		}
		return args;
	}
}
