/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runResults;

import fit.Counts;

public interface TestResults {
	Counts getCounts();

	void add(TestResults innerResults);

	boolean passed();

	void clear();

	boolean errors();

	boolean failed();

	boolean problems();

	void exception();

	void ignore();

	void fail();

	void pass();

	boolean matches(String text, String text2, String text3, String text4);

	void addRights(int i);
}
