/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 23/09/2006
 */

package fitlibraryGeneric.typed;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.UnboundTypeException;
import fitlibrary.object.Finder;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserSelectorForType;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;
import fitlibrary.utility.ClassUtility;
import fitlibraryGeneric.generic.GenericTypeUtility;
import fitlibraryGeneric.generic.LocalGenericArrayType;
import fitlibraryGeneric.generic.LocalParameterizedType;
import fitlibraryGeneric.generic.GenericTypeUtility.GenericCases;
import fitlibraryGeneric.object.GenericFinder;

public class GenericTyped implements Typed {
	private Type type;
	private boolean hasMethodOrField;
	private Map<TypeVariable<?>, Type> bindings = new HashMap<TypeVariable<?>, Type>();
	private static ParserSelectorForType parserSelector = new ParserSelectorForType();

	public GenericTyped(Type type) {
		this.type = type;
		if (isGeneric())
			makeTypeBindings(asParameterizedType(), asParameterizedType().getActualTypeArguments(), "");
	}

	public GenericTyped(Type type, boolean hasMethodOrField) {
		this(type);
		this.hasMethodOrField = hasMethodOrField;
	}

	// @Override
	public Class<?> asClass() {
		return GenericTypeUtility.getClassType(type);
	}

	// @Override
	public boolean hasMethodOrField() {
		return hasMethodOrField;
	}

	// @Override
	public GenericTyped getComponentTyped() {
		return new GenericTyped(getComponentType());
	}

	public GenericTyped getComponentTyped(int index) {
		return new GenericTyped(getComponentType(index));
	}

	// @Override
	public boolean isPrimitive() {
		return asClass().isPrimitive();
	}

	public boolean isEffectivelyPrimitive() {
		return ClassUtility.isEffectivelyPrimitive(asClass());
	}

	// @Override
	public boolean isGeneric() {
		return type instanceof ParameterizedType;
	}

	public Type getComponentType() {
		return getComponentType(0);
	}

	public Type getComponentType(int index) {
		switch (GenericTypeUtility.typeCases(type)) {
		case CLASS_TYPE:
			Class<?> classType = (Class<?>) type;
			if (index == 0)
				if (classType.isArray())
					return classType.getComponentType();
			throw new FitLibraryException("A " + type + " doesn't have a component type");
		case PARAMETERIZED_TYPE:
			ParameterizedType pType = (ParameterizedType) type;
			if (pType.getActualTypeArguments().length > index)
				return pType.getActualTypeArguments()[index];
			break;
		case GENERIC_ARRAY:
			if (index == 0) {
				GenericArrayType aType = (GenericArrayType) type;
				return aType.getGenericComponentType();
			}
			break;
		case TYPE_VARIABLE:
		case WILDCARD_TYPE:
			throw new FitLibraryException("A " + type + " doesn't have a component type");
		}
		return null;
	}

	public GenericTyped bindToGenericType(Type givenType, String context) {
		return new GenericTyped(bind(givenType, context));
	}

	public Type bind(Type givenType, String context) {
		Type resultingType = null;
		switch (GenericTypeUtility.typeCases(givenType)) {
		case TYPE_VARIABLE:
			resultingType = bindings.get(givenType);
			if (resultingType == null)
				throw new UnboundTypeException(givenType, context);
			break;
		case PARAMETERIZED_TYPE:
			ParameterizedType parameterizedType = GenericTypeUtility.asParameterizedType(givenType);
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			resultingType = new LocalParameterizedType(parameterizedType.getOwnerType(),
					parameterizedType.getRawType(), bind(actualTypeArguments, context));
			break;
		case CLASS_TYPE:
			resultingType = givenType;
			break;
		case GENERIC_ARRAY:
			Type genericComponentType = ((GenericArrayType) givenType).getGenericComponentType();
			LocalGenericArrayType local = new LocalGenericArrayType(bind(genericComponentType, context));
			if (GenericTypeUtility.typeCases(local.getGenericComponentType()) == GenericCases.CLASS_TYPE) {
				resultingType = local.asClass();
			} else
				resultingType = local;
			break;
		case WILDCARD_TYPE:
			throw new RuntimeException("Unable to handle wildcard types");
		}
		if (resultingType == null || !isFullyBound(resultingType))
			throw new UnboundTypeException(resultingType, context);
		return resultingType;
	}

	private boolean isFullyBound(Type givenType) {
		switch (GenericTypeUtility.typeCases(givenType)) {
		case TYPE_VARIABLE:
		case WILDCARD_TYPE:
			return false;
		case CLASS_TYPE:
			return true;
		case GENERIC_ARRAY:
			GenericArrayType genericArrayType = (GenericArrayType) givenType;
			return isFullyBound(genericArrayType.getGenericComponentType());
		case PARAMETERIZED_TYPE:
			ParameterizedType parameterizedType = (ParameterizedType) givenType;
			for (Type t : parameterizedType.getActualTypeArguments())
				if (!isFullyBound(t))
					return false;
		}
		return true;
	}

	public GenericTyped[] bindToGenericTypes(Type[] types, String context) {
		GenericTyped[] results = new GenericTyped[types.length];
		for (int i = 0; i < types.length; i++) {
			results[i] = bindToGenericType(types[i], context);
		}
		return results;
	}

	public Type[] bind(Type[] types, String context) {
		Type[] results = new Type[types.length];
		for (int i = 0; i < types.length; i++) {
			results[i] = bind(types[i], context);
		}
		return results;
	}

	private ParameterizedType asParameterizedType() {
		return (ParameterizedType) type;
	}

	public void assertHasParameters(int expectedCount) {
		if (actualCount() != expectedCount)
			throw new RuntimeException("Expected " + expectedCount + "type parameters, but found " + actualCount());
	}

	private int actualCount() {
		return asParameterizedType().getActualTypeArguments().length;
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
	public String toString() {
		return GenericTypeUtility.toString(type);
	}

	// @Override
	public String simpleClassName() {
		return ClassUtility.simpleClassName(asClass());
	}

	// @Override
	public TypedObject typedObject(Object subject) {
		return new GenericTypedObject(subject, this);
	}

	// @Override
	public boolean isArray() {
		return asClass().isArray();
	}

	// @Override
	public TypedObject newTypedInstance() throws InstantiationException, IllegalAccessException, SecurityException,
			IllegalArgumentException, NoSuchMethodException, InvocationTargetException {
		return typedObject(newInstance());
	}

	private void makeTypeBindings(ParameterizedType parameterisedType, Type[] actualTypeArguments, String context) {
		Class<?> rawType = (Class<?>) parameterisedType.getRawType();
		TypeVariable<?>[] formalTypeParameters = rawType.getTypeParameters();
		if (formalTypeParameters.length != actualTypeArguments.length)
			throw new RuntimeException("Lengths not the same for the formal and actual type arguments");
		for (int i = 0; i < formalTypeParameters.length; i++) {
			bindings.put(formalTypeParameters[i], actualTypeArguments[i]);
		}
		makeTypeBindingsForSuperClasses(rawType, context);
	}

	private void makeTypeBindingsForSuperClasses(Class<?> rawType, String context) {
		if (rawType == null)
			return;
		Type genericSuperclass = rawType.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType)
			makeTypeBindings((ParameterizedType) genericSuperclass,
					bind(((ParameterizedType) genericSuperclass).getActualTypeArguments(), context), context);
		else
			makeTypeBindingsForSuperClasses(rawType.getSuperclass(), context);
	}

	public GenericCases typeCases() {
		return GenericTypeUtility.typeCases(type);
	}

	// @Override
	public Parser parser(Evaluator evaluator) {
		return on(evaluator, this, false);
	}

	// @Override
	public Parser resultParser(Evaluator evaluator) {
		return on(evaluator, this, true);
	}

	// @Override
	public Parser on(Evaluator evaluator, Typed typed, boolean isResult) {
		return parserSelector.parserFor(evaluator, typed, isResult);
	}

	// @Override
	public boolean isEnum() {
		return asClass().isEnum();
	}

	// @Override
	public Finder getFinder(Evaluator evaluator) {
		return new GenericFinder(this, evaluator);
	}

	public Type asType() {
		return type;
	}
}
