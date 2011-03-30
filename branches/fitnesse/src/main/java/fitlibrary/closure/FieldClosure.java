/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ResultParser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;

public class FieldClosure implements Closure {
	private static Logger logger = FitLibraryLogger.getLogger(MethodClosure.class);
	private Field field;
	private TypedObject typedObject;
	private int runAlready = 0;

	public FieldClosure(TypedObject typedObject, Field field) {
		this.typedObject = typedObject;
		this.field = field;
	}

	// @Override
	public Class<?>[] getParameterTypes() {
		return new Class[0];
	}

	// @Override
	public Class<?> getReturnType() {
		return field.getType();
	}

	// @Override
	public Object invoke() throws IllegalAccessException, InvocationTargetException {
		if (runAlready < MethodClosure.MAX_RUNS_SHOWN)
			logger.trace("Calling " + this); // Avoid blowing the heap with lots
												// of logs
		else if (runAlready == MethodClosure.MAX_RUNS_SHOWN)
			logger.trace("Calling " + this + "(etc)");
		runAlready++;
		return field.get(typedObject.getSubject());
	}

	// @Override
	public Object invoke(Object[] arguments) throws IllegalAccessException, InvocationTargetException {
		return invoke();
	}

	// @Override
	public TypedObject invokeTyped(Object[] arguments) throws IllegalAccessException, InvocationTargetException {
		return typedObject.asReturnTypedObject(invoke(), field);
	}

	// @Override
	public Parser[] parameterParsers(Evaluator evaluator) {
		return new Parser[0];
	}

	// @Override
	public ResultParser resultParser(Evaluator evaluator) {
		return typedObject.resultParser(evaluator, field);
	}

	// @Override
	public ResultParser specialisedResultParser(ResultParser resultParser, Object result, Evaluator evaluator) {
		if (result == null || result.getClass() == field.getType())
			return resultParser;
		return typedObject.resultParser(evaluator, field, result.getClass());
	}

	// @Override
	public void setTypedSubject(TypedObject typedObject) {
		this.typedObject = typedObject;
	}

	@Override
	public String toString() {
		return field.getName() + " on " + typedObject.getSubject();
	}

	// @Override
	public Class<?> getOwningClass() {
		return typedObject.getSubject().getClass();
	}
}
