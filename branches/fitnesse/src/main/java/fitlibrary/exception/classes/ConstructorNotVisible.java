/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.classes;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.utility.ExtendedCamelCase;

public class ConstructorNotVisible extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public ConstructorNotVisible(String className, RuntimeContextInternal runtime) {
		super("Constructor for class is not visible: "
				+ ExtendedCamelCase.camelClassName(className, runtime.getConfiguration().keepingUniCode()));
	}
}
