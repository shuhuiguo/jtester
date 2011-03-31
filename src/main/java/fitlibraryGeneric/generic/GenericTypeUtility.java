/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 24/09/2006
 */

package fitlibraryGeneric.generic;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import fitlibrary.exception.FitLibraryException;

public class GenericTypeUtility {
	public enum GenericCases {
		GENERIC_ARRAY, PARAMETERIZED_TYPE, TYPE_VARIABLE, WILDCARD_TYPE, CLASS_TYPE
	}

	public static GenericCases typeCases(Type type) {
		if (type instanceof ParameterizedType)
			return GenericCases.PARAMETERIZED_TYPE;
		if (type instanceof TypeVariable)
			return GenericCases.TYPE_VARIABLE;
		if (type instanceof GenericArrayType)
			return GenericCases.GENERIC_ARRAY;
		if (type instanceof WildcardType)
			return GenericCases.WILDCARD_TYPE;
		return GenericCases.CLASS_TYPE;
	}

	public static Class<?> getClassType(Type type) {
		switch (typeCases(type)) {
		case CLASS_TYPE:
			return asClass(type);
		case PARAMETERIZED_TYPE:
			return getClassType(asParameterizedType(type).getRawType());
		case GENERIC_ARRAY:
			return asClass((GenericArrayType) type);
		case TYPE_VARIABLE:
		case WILDCARD_TYPE:
			throw new RuntimeException("Unable to deal with type " + type + " of " + type.getClass());
		}
		throw new RuntimeException("Unable to deal with type " + type);
	}

	public static Class<?> asClass(GenericArrayType type) {
		try {
			return Array.newInstance(getClassType(type.getGenericComponentType()), 0).getClass();
		} catch (ClassCastException e) {
			throw new FitLibraryException("Unable to deal with unbound type " + type);
		}
	}

	public static String toString(Type type) {
		if (type == null)
			throw new RuntimeException("Type is null");
		return type.toString();
	}

	public static GenericArrayType asGenericArrayType(Type type) {
		return (GenericArrayType) type;
	}

	public static ParameterizedType asParameterizedType(Type type) {
		return (ParameterizedType) type;
	}

	public static TypeVariable<?> asTypeVariable(Type type) {
		return (TypeVariable<?>) type;
	}

	public static WildcardType asWildcardType(Type type) {
		return (WildcardType) type;
	}

	public static Class<?> asClass(Type type) {
		return (Class<?>) type;
	}

}
