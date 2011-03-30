/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.table;

import fit.Parse;
import fitlibrary.runResults.TestResults;

public interface TableElement<From, To> extends Iterable<To> {
	From add(To t);

	int size();

	boolean isEmpty();

	To at(int i);

	boolean atExists(int i);

	To last();

	From fromAt(int i);

	Iterable<To> iterableFrom(int start);

	From fromTo(int from, int upto);

	From deepCopy();

	void setLeader(String leader);

	void setTrailer(String trailer);

	String getLeader();

	String getTrailer();

	String getTagLine();

	void setTagLine(String tagLine);

	String getType();

	void addToTag(String report);

	void error(TestResults testResults, Throwable e);

	void toHtml(StringBuilder builder);

	Parse parse();
}
