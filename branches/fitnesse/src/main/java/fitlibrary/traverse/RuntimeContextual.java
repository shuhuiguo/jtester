/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse;

import fitlibrary.runtime.RuntimeContextInternal;

public interface RuntimeContextual extends DomainAdapter {
	void setRuntimeContext(RuntimeContextInternal runtime);
}
