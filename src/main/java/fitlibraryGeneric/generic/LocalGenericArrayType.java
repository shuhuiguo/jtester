/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 5/11/2006
 */

package fitlibraryGeneric.generic;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

public class LocalGenericArrayType implements GenericArrayType {
	private Type genericComponentType;

	public LocalGenericArrayType(Type genericComponentType) {
		this.genericComponentType = genericComponentType;
	}

	// @Override
	public Type getGenericComponentType() {
		return genericComponentType;
	}

	@Override
	public String toString() {
		return genericComponentType.toString() + "[]";
	}

	public Type asClass() {
		return GenericTypeUtility.asClass(this);
	}
}
