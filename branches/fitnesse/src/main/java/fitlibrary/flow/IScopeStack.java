/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import java.util.List;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.typed.TypedObject;

public interface IScopeStack extends IScope {
	void clearAllButSuite();

	void push(TypedObject typedObject);

	List<TypedObject> poppedAtEndOfTable();

	List<TypedObject> poppedAtEndOfStorytest();

	TypedObject pop();

	IScopeState currentState();

	void select(String name);

	void switchRuntime(RuntimeContextInternal runtime);
}