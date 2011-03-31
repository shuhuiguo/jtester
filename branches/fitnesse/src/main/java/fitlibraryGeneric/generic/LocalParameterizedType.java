/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 3/11/2006
 */

package fitlibraryGeneric.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class LocalParameterizedType implements ParameterizedType {

	private Type ownerType;
	private Type rawType;
	private Type[] actualTypeArguments;

	public LocalParameterizedType(Type ownerType, Type rawType, Type... actualTypeArguments) {
		this.ownerType = ownerType;
		this.rawType = rawType;
		this.actualTypeArguments = actualTypeArguments;
	}

	// public LocalParameterizedType(Type ownerType, Type rawType, Type[]
	// actualTypeArguments) {
	// this.ownerType = ownerType;
	// this.rawType = rawType;
	// this.actualTypeArguments = actualTypeArguments;
	// }
	// @Override
	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}

	// @Override
	public Type getOwnerType() {
		return ownerType;
	}

	// @Override
	public Type getRawType() {
		return rawType;
	}

	@Override
	public String toString() {
		String args = actualTypeArguments[0].toString();
		for (int i = 1; i < actualTypeArguments.length; i++)
			args += ", " + actualTypeArguments[i].toString();
		return rawType + "<" + args + ">";
	}
}
