/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.lookup;

import fitlibrary.parser.Parser;

public interface ResultParser extends Parser {
	boolean isShowAsHtml();

	void setTarget(Object target);

	Object getResult() throws Exception;
}
