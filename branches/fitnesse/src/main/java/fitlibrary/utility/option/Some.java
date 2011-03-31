/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.utility.option;

public class Some<T> implements Option<T> {
	private T t;

	public Some(T t) {
		this.t = t;
	}

	// @Override
	public boolean isNone() {
		return false;
	}

	// @Override
	public boolean isSome() {
		return true;
	}

	// @Override
	public T get() {
		return t;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Some))
			return false;
		return t.equals(((Some) obj).t);
	}

	@Override
	public int hashCode() {
		return t.hashCode();
	}
}
