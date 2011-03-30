/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runtime;

import java.io.IOException;

import fitlibrary.dynamicVariable.DynamicVariables;
import fitlibrary.dynamicVariable.VariableResolver;

public interface RuntimeContext extends VariableResolver {
	Object getDynamicVariable(String key);

	DynamicVariables getDynamicVariables();

	VariableResolver getResolver();

	void printToLog(String s) throws IOException;

	void setAbandon(boolean b);

	void setDynamicVariable(String key, Object value);

	void setStopOnError(boolean stop);

	void show(String s);

	void showAsAfterTable(String title, String s);
}
