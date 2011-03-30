/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tableProxy;

public interface CellProxy {
	void pass();

	void pass(String msg);

	void fail(String msg);

	void failHtml(String html);

	void error(String msg);

	void error(Throwable e);

	void fail();

	void error();
}
