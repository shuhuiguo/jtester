/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibrary.typed;

import java.lang.reflect.InvocationTargetException;

import fitlibrary.object.Finder;
import fitlibrary.object.NonGenericFinder;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserSelectorForType;
import fitlibrary.traverse.Evaluator;
import fitlibrary.utility.ClassUtility;
import fitlibraryGeneric.typed.GenericTypedObject;

public class NonGenericTyped implements Typed {
	private Class<?> classType;
	private boolean hasMethodOrField;
	private static ParserSelectorForType parserSelector = new ParserSelectorForType();

	public NonGenericTyped(Class<?> classType) {
		this.classType = classType;
	}

	public NonGenericTyped(Class<?> classType, boolean hasMethodOrField) {
		this(classType);
		this.hasMethodOrField = hasMethodOrField;
	}

	// @Override
	public Class<?> asClass() {
		return classType;
	}

	// @Override
	public boolean hasMethodOrField() {
		return hasMethodOrField;
	}

	// @Override
	public Typed getComponentTyped() {
		return new NonGenericTyped(classType.getComponentType());
	}

	// @Override
	public boolean isPrimitive() {
		return classType.isPrimitive();
	}

	// @Override
	public boolean isArray() {
		return classType.isArray();
	}

	// @Override
	public boolean isGeneric() {
		return false;
	}

	// @Override
	public boolean isEnum() {
		return asClass().isEnum();
	}

	// @Override
	public Object newInstance() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		return ClassUtility.newInstance(asClass());
	}

	// @Override
	public String getClassName() {
		return asClass().getName();
	}

	// @Override
	public String simpleClassName() {
		return ClassUtility.simpleClassName(asClass());
	}

	// @Override
	public TypedObject typedObject(Object subject) {
		return new GenericTypedObject(subject);
	}

	// @Override
	public TypedObject newTypedInstance() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		return typedObject(newInstance());
	}

	// @Override
	public Parser parser(Evaluator evaluator) {
		return parserSelector.parserFor(evaluator, this, false);
	}

	// @Override
	public Parser resultParser(Evaluator evaluator) {
		return parserSelector.parserFor(evaluator, this, true);
	}

	// @Override
	public Parser on(Evaluator evaluator, Typed typed, boolean isResult) {
		return parserSelector.parserFor(evaluator, typed, isResult);
	}

	@Override
	public String toString() {
		return classType.toString();
	}

	// @Override
	public Finder getFinder(Evaluator evaluator) {
		return new NonGenericFinder(this, evaluator);
	}
}
