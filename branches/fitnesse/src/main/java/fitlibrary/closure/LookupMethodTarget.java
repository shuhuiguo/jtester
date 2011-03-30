/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.closure;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import fitlibrary.flow.IScope;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.special.PositionedTarget;
import fitlibrary.table.Row;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.caller.ValidCall;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.MustBeThreadSafe;

public interface LookupMethodTarget extends MustBeThreadSafe {
	ICalledMethodTarget findSpecialMethod(Evaluator evaluator, String name);

	ICalledMethodTarget findPostfixSpecialMethod(Evaluator evaluator, String name);

	Closure findFixturingMethod(Evaluator evaluator, String name, Class<?>[] argTypes);

	ICalledMethodTarget findTheMethodMapped(String name, int argCount, Evaluator evaluator) throws Exception;

	ICalledMethodTarget findMethodOrGetter(String name, List<String> methodArgs, String returnType, Evaluator evaluator)
			throws Exception;

	ICalledMethodTarget findMethod(String name, List<String> methodArgs, String returnType, Evaluator evaluator);

	ICalledMethodTarget findSetterOnSut(String propertyName, Evaluator evaluator);

	ICalledMethodTarget findGetterOnSut(String propertyName, Evaluator evaluator, String returnType);

	ICalledMethodTarget findGetterUpContextsToo(TypedObject typedObject, Evaluator evaluator, String propertyName,
			boolean considerContext);

	List<Class<?>> possibleClasses(IScope iScope);

	Class<?> findClassFromFactoryMethod(Evaluator evaluator, Class<?> type, String typeName)
			throws IllegalAccessException, InvocationTargetException;

	Closure findNewInstancePluginMethod(Evaluator evaluator);

	void findMethodsFromPlainText(String textCall, List<ValidCall> results, RuntimeContextInternal runtime);

	List<PositionedTarget> findActionSpecialMethod(Evaluator evaluator, String[] cells, boolean sequencing);

	ICalledMethodTarget findMethodByArity(Row row, int from, int upTo, boolean doStyle, Evaluator evaluator)
			throws Exception;
}