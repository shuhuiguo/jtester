/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow;

public class StopWatch {
	private long start = System.currentTimeMillis();

	public long delay() {
		return System.currentTimeMillis() - start;
	}
}
