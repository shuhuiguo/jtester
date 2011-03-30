/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.definedAction;

import java.util.List;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.traverse.workflow.caller.ValidCall;

public interface DefinedActionsRepository {
	void clear();

	void define(Row parametersRow, String wikiClassName, ParameterBinder parameterSubstitution,
			RuntimeContextInternal runtime, String absoluteFileName);

	ParameterBinder lookupByCamel(String name, int argCount);

	ParameterBinder lookupByClassByCamel(String className, String name, int argCount, RuntimeContextInternal variables);

	void findPlainTextCall(String textCall, List<ValidCall> results);

	ParameterBinder lookupMulti(String name);

	void defineMultiDefinedAction(String name, ParameterBinder parameterSubstitution);
}
