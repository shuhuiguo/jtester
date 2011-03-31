/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.utility.option;

@SuppressWarnings("rawtypes")
public class None<T> implements Option<T> {
	private static None NONE = new None();

	@SuppressWarnings("unchecked")
	public static <S> None<S> none() {
		return NONE;
	}

	private None() {
		//
	}

	// @Override
	public boolean isNone() {
		return true;
	}

	// @Override
	public boolean isSome() {
		return false;
	}

	// @Override
	public T get() {
		throw new NullPointerException("There is no element inside None");
	}
}
