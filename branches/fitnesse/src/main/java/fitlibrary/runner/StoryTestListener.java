/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

public interface StoryTestListener {
	void testComplete(boolean failing, String pageCounts, String assertionCounts);

	void reportOutput(String name, String out, String output);

	void suiteComplete();
}
