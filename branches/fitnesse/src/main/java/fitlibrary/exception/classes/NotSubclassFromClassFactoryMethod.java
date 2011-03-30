/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 21/09/2006
 */

package fitlibrary.exception.classes;

import fitlibrary.exception.FitLibraryException;

public class NotSubclassFromClassFactoryMethod extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public NotSubclassFromClassFactoryMethod(Class<?> sutClass, Class<?> type) {
		super("Not a subclass: class " + sutClass.getName() + " is not a subclass of " + type.getName());
	}

}
