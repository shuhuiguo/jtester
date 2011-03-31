/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 21/10/2006
 */

package fitlibrary.typed;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import fitlibrary.closure.Closure;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ResultParser;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.special.PositionedTarget;
import fitlibrary.special.PositionedTargetFactory;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.caller.ValidCall;
import fitlibrary.utility.option.Option;

public interface TypedObject {
	public Object getSubject();

	public Closure findPublicMethodClosureForTypedObject(String name, Class<?>[] args);

	public ICalledMethodTarget new_optionallyFindGetterOnTypedObject(String propertyName, Evaluator evaluator);

	public Class<?> classType();

	public Parser[] parameterParsers(Evaluator evaluator, Method method);

	public ResultParser resultParser(Evaluator evaluator, Method method);

	public ResultParser resultParser(Evaluator evaluator, Field field);

	public ResultParser resultParser(Evaluator evaluator, Method method, Class<?> actualResultType);

	public ResultParser resultParser(Evaluator evaluator, Field field, Class<?> class1);

	public TypedObject asReturnTypedObject(Object object, Method method);

	public TypedObject asReturnTypedObject(Object object, Field field);

	public Evaluator traverse(Evaluator evaluator);

	public Typed getTyped();

	public void findMethodsFromPlainText(String textCall, List<ValidCall> results, RuntimeContextInternal runtime);

	Option<ICalledMethodTarget> new_findSpecificMethod(String methodName, int argCount, Evaluator evaluator);

	public boolean isNull();

	boolean hasTypedSystemUnderTest();

	TypedObject getTypedSystemUnderTest();

	void injectRuntime(RuntimeContextInternal runtime);

	public List<PositionedTarget> findActionSpecialMethods(String[] cells, PositionedTargetFactory factory,
			RuntimeContextInternal runtime);
}
