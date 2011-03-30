/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 16/12/2006
 */

package fitlibrary.suite;

public interface FlowControl {
	void abandon();

	void setStopOnError(boolean stopOnError);
}
