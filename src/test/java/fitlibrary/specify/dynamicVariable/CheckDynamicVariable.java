/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.specify.dynamicVariable;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.traverse.RuntimeContextual;

public class CheckDynamicVariable implements RuntimeContextual {
	private RuntimeContextInternal runtime;

	public boolean checkAs(String var, String s) {
		if (!s.equals("<a><user password=\"kiwi\">rick</user><a>"))
			throw new FitLibraryException(var + " was " + s);
		Object dynamicVariable = runtime.getDynamicVariable(var);
		if (!dynamicVariable.equals("<a><user password=\"kiwi\">rick</user><a>"))
			throw new FitLibraryException(var + " internally was " + dynamicVariable);
		return true;
	}

	// @Override
	public void setRuntimeContext(RuntimeContextInternal runtime) {
		this.runtime = runtime;
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
