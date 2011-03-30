/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.flow;

import fitlibrary.typed.TypedObject;

public interface IDoAutoWrapper {
	public abstract TypedObject wrap(TypedObject typedResult);

	public abstract boolean canAutoWrap(Object result);
}