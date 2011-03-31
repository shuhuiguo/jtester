/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.utility;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.traverse.RuntimeContextual;

public class CamelCase implements RuntimeContextual {
	private RuntimeContextInternal runtime;

	public String identifierName(String name) {
		return runtime.extendedCamel(name);
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}

	// @Override
	public void setRuntimeContext(RuntimeContextInternal runtime) {
		this.runtime = runtime;
	}
}
