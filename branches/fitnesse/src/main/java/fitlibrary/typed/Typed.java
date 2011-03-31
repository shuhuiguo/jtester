/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibrary.typed;

import java.lang.reflect.InvocationTargetException;

import fitlibrary.object.Finder;
import fitlibrary.parser.Parser;
import fitlibrary.traverse.Evaluator;

public interface Typed {
	Class<?> asClass();

	boolean hasMethodOrField();

	boolean isGeneric();

	Object newInstance() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException;

	String getClassName();

	String simpleClassName();

	TypedObject typedObject(Object subject);

	boolean isArray();

	Typed getComponentTyped();

	TypedObject newTypedInstance() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException;

	Parser parser(Evaluator evaluator);

	Parser resultParser(Evaluator evaluator);

	Parser on(Evaluator evaluator, Typed typed, boolean isResult);

	boolean isPrimitive();

	boolean isEnum();

	Finder getFinder(Evaluator evaluator);
}
