/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 10/12/2006
 */

package fitlibrary.suite;

import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.traverse.workflow.DoEvaluator;

public interface SuiteEvaluator extends DoEvaluator {
	RuntimeContextInternal getCopyOfRuntimeContext();
}
