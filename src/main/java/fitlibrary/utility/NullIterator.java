/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.util.Iterator;

public class NullIterator<T> implements Iterator<T> {
	private Iterator<T> it;

	public NullIterator(Iterator<T> it) {
		this.it = it;
	}

	// @Override
	public boolean hasNext() {
		return it.hasNext();
	}

	// @Override
	public T next() {
		if (it.hasNext())
			return it.next();
		return null;
	}

	// @Override
	public void remove() {
		it.remove();
	}

	public boolean end(Object obj) {
		return obj == null;
	}
}
