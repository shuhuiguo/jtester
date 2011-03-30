/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.closure;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import fitlibrary.exception.NoSystemUnderTestException;
import fitlibrary.exception.method.MissingMethodException;
import fitlibrary.flow.IScope;
import fitlibrary.global.PlugBoard;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.special.PositionedTarget;
import fitlibrary.special.PositionedTargetFactory;
import fitlibrary.special.PositionedTargetWasFound;
import fitlibrary.table.Row;
import fitlibrary.traverse.DomainAdapter;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.workflow.caller.ValidCall;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ClassUtility;
import fitlibrary.utility.option.Option;
import fitlibraryGeneric.typed.GenericTypedObject;

public class LookupMethodTargetStandard implements LookupMethodTarget {
	@SuppressWarnings("unused")
	private static Logger logger = FitLibraryLogger.getLogger(LookupMethodTargetStandard.class);

	// @Override
	public void mustBeThreadSafe() {
		//
	}

	// @Override
	public CalledMethodTarget findSpecialMethod(Evaluator evaluator, String name) {
		if (name.equals(""))
			return null;
		Closure findEntityMethod = findFixturingMethod(evaluator, evaluator.getRuntimeContext().extendedCamel(name),
				new Class[] { Row.class, TestResults.class });
		if (findEntityMethod == null)
			findEntityMethod = findFixturingMethod(evaluator, evaluator.getRuntimeContext().extendedCamel(name),
					new Class[] { Row.class });
		if (findEntityMethod == null)
			return null;
		return new CalledMethodTarget(findEntityMethod, evaluator);
	}

	// @Override
	public CalledMethodTarget findPostfixSpecialMethod(Evaluator evaluator, String name) {
		if (name.equals(""))
			return null;
		Closure findEntityMethod = findFixturingMethod(evaluator, evaluator.getRuntimeContext().extendedCamel(name),
				new Class[] { TestResults.class, Row.class });
		if (findEntityMethod == null)
			return null;
		return new CalledMethodTarget(findEntityMethod, evaluator);
	}

	// @Override
	public Closure findFixturingMethod(Evaluator evaluator, String name, Class<?>[] argTypes) {
		IScope scope = evaluator.getScope();
		for (TypedObject typedObject : scope.objectsForLookup()) {
			Closure target = typedObject.findPublicMethodClosureForTypedObject(name, argTypes);
			if (target != null)
				return target;
		}
		return null;
	}

	// @Override
	public ICalledMethodTarget findMethodByArity(Row row, int from, int upTo, boolean doStyle, Evaluator evaluator)
			throws Exception {
		ActionSignature actionSignature = ActionSignature.create(row, from, upTo, doStyle,
				evaluator.getRuntimeContext());
		ICalledMethodTarget target = findTheMethodMapped(actionSignature.name, actionSignature.arity, evaluator);
		target.setEverySecond(doStyle);
		return target;
	}

	// @Override
	public ICalledMethodTarget findTheMethodMapped(String name, int argCount, Evaluator evaluator) throws Exception {
		return findMethodOrGetter(evaluator.getRuntimeContext().extendedCamel(name), unknownParameterNames(argCount),
				"Type", evaluator);
	}

	private static List<String> unknownParameterNames(int argCount) {
		List<String> methodArgs = new ArrayList<String>();
		for (int i = 0; i < argCount; i++)
			methodArgs.add("arg" + (i + 1));
		return methodArgs;
	}

	// @Override
	public ICalledMethodTarget findMethodOrGetter(String name, List<String> methodArgs, String returnType,
			Evaluator evaluator) throws Exception {
		int argCount = methodArgs.size();
		IScope scope = evaluator.getScope();
		for (TypedObject typedObject : scope.objectsForLookup()) {
			Option<ICalledMethodTarget> target = typedObject.new_findSpecificMethod(name, argCount, evaluator);
			if (target.isSome())
				return target.get();
			if (argCount == 0) {
				String getMethodName = evaluator.getRuntimeContext().extendedCamel("get " + name);
				target = typedObject.new_findSpecificMethod(getMethodName, argCount, evaluator);
				if (target.isSome())
					return target.get();
				String isMethodName = evaluator.getRuntimeContext().extendedCamel("is " + name);
				target = typedObject.new_findSpecificMethod(isMethodName, argCount, evaluator);
				if (target.isSome())
					return target.get();
			}
		}
		List<String> signatures = ClassUtility.methodSignatures(name, methodArgs, returnType);
		throw new MissingMethodException(signatures, scope.possibleClasses());
	}

	// @Override
	public ICalledMethodTarget findMethod(String name, List<String> methodArgs, String returnType, Evaluator evaluator) {
		int argCount = methodArgs.size();
		IScope scope = evaluator.getScope();
		for (TypedObject typedObject : scope.objectsForLookup()) {
			Option<ICalledMethodTarget> target = typedObject.new_findSpecificMethod(name, argCount, evaluator);
			if (target.isSome())
				return target.get();
		}
		List<String> signatures = ClassUtility.methodSignatures(name, methodArgs, returnType);
		throw new MissingMethodException(signatures, scope.possibleClasses());
	}

	// @Override
	public ICalledMethodTarget findSetterOnSut(String propertyName, Evaluator evaluator) {
		return findMethodOnSut(evaluator.getRuntimeContext().extendedCamel(("set " + propertyName)), 1, evaluator,
				"ArgType " + evaluator.getRuntimeContext().extendedCamel(propertyName), "void");
	}

	// @Override
	public ICalledMethodTarget findGetterOnSut(String propertyName, Evaluator evaluator, String returnType) {
		return findMethodOnSut(evaluator.getRuntimeContext().extendedCamel(("get " + propertyName)), 0, evaluator, "",
				returnType);
	}

	private ICalledMethodTarget findMethodOnSut(String methodName, int argCount, Evaluator evaluator, String arg,
			String returnType) {
		TypedObject typedObject = evaluator.getTypedSystemUnderTest();
		while (true) {
			if (typedObject.isNull())
				throw new NoSystemUnderTestException();
			Option<ICalledMethodTarget> targetOption = typedObject.new_findSpecificMethod(methodName, argCount,
					evaluator);
			if (targetOption.isSome())
				return targetOption.get();
			if (typedObject instanceof Evaluator) {
				typedObject = ((Evaluator) typedObject).getTypedSystemUnderTest();
			} else if (typedObject.getSubject() instanceof DomainAdapter) {
				typedObject = new GenericTypedObject(((DomainAdapter) typedObject.getSubject()).getSystemUnderTest());
			} else
				break;
		}
		throw new MissingMethodException(signatures("public " + returnType + " " + methodName + "(" + arg + ") { }"),
				evaluator.getScope().possibleClasses());
	}

	// @Override
	public ICalledMethodTarget findGetterUpContextsToo(TypedObject typedObject, Evaluator evaluator,
			String propertyName, boolean considerContext) {
		ICalledMethodTarget target;
		if (considerContext)
			target = searchForMethodTargetUpOuterContext(propertyName, evaluator);
		else
			target = typedObject.new_optionallyFindGetterOnTypedObject(propertyName, evaluator);
		if (target != null)
			return target;
		List<Class<?>> possibleClasses = new ArrayList<Class<?>>();
		if (considerContext)
			possibleClasses = evaluator.getScope().possibleClasses();
		else
			possibleClasses.add(typedObject.classType());
		throw new MissingMethodException(signatures("public ResultType "
				+ evaluator.getRuntimeContext().extendedCamel(("get " + propertyName)) + "() { }"), possibleClasses);
	}

	private List<String> signatures(String... signature) {
		return Arrays.asList(signature);
	}

	private ICalledMethodTarget searchForMethodTargetUpOuterContext(String name, Evaluator evaluator) {
		IScope scope = evaluator.getScope();
		for (TypedObject typedObject : scope.objectsForLookup()) {
			ICalledMethodTarget target = typedObject.new_optionallyFindGetterOnTypedObject(name, evaluator);
			if (target != null)
				return target;
		}
		return null;
	}

	// @Override
	public List<Class<?>> possibleClasses(IScope scope) {
		return scope.possibleClasses();
	}

	// @Override
	public Class<?> findClassFromFactoryMethod(Evaluator evaluator, Class<?> type, String typeName)
			throws IllegalAccessException, InvocationTargetException {
		String methodName = "concreteClassOf" + ClassUtility.simpleClassName(type);
		Closure method = findFixturingMethod(evaluator, methodName, new Class[] { String.class });
		if (method == null) {
			throw new MissingMethodException(signatures("public Class " + methodName + "(String typeName) { }"),
					evaluator.getScope().possibleClasses());
		}
		return (Class<?>) method.invoke(new Object[] { typeName });
	}

	// @Override
	public Closure findNewInstancePluginMethod(Evaluator evaluator) {
		return findFixturingMethod(evaluator, "newInstancePlugin", new Class[] { Class.class });
	}

	// @Override
	public void findMethodsFromPlainText(String textCall, List<ValidCall> results, RuntimeContextInternal runtime) {
		int size = results.size();
		for (TypedObject typedObject : runtime.getScope().objectsForLookup()) {
			typedObject.findMethodsFromPlainText(textCall, results, runtime);
			if (results.size() > size)
				return;
		}
	}

	// @Override
	public List<PositionedTarget> findActionSpecialMethod(final Evaluator evaluator, final String[] cells,
			final boolean sequencing) {
		for (final TypedObject typedObject : evaluator.getScope().objectsForLookup()) {
			List<PositionedTarget> positioned = typedObject.findActionSpecialMethods(cells,
					new PositionedTargetFactory() {
						// @Override
						public PositionedTarget create(Method method, int from, int upTo) {
							return new PositionedTargetWasFound(evaluator, cells, typedObject, method, from, upTo,
									sequencing, PlugBoard.lookupTarget);
						}
					}, evaluator.getRuntimeContext());
			if (!positioned.isEmpty())
				return positioned;
		}
		return new ArrayList<PositionedTarget>();
	}
}
