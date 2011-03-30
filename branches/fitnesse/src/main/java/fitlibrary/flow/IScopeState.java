/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import java.util.List;

import fitlibrary.typed.TypedObject;

public interface IScopeState {
	List<TypedObject> restore();
}
