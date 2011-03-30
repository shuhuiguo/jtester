/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import java.lang.reflect.InvocationTargetException;

import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ResultParser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.TypedObject;

public interface Closure {
	Parser[] parameterParsers(Evaluator evaluator);

	ResultParser resultParser(Evaluator evaluator);

	ResultParser specialisedResultParser(ResultParser resultParser, Object result, Evaluator evaluator);

	Class<?> getReturnType();

	Class<?>[] getParameterTypes();

	void setTypedSubject(TypedObject typedObject);

	Object invoke() throws IllegalAccessException, InvocationTargetException;

	Object invoke(Object[] arguments) throws IllegalAccessException, InvocationTargetException;

	TypedObject invokeTyped(Object[] arguments) throws IllegalAccessException, InvocationTargetException;

	Class<?> getOwningClass();
}
