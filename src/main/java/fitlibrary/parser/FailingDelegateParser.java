/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser;

import fitlibrary.exception.parse.CouldNotParseException;
import fitlibrary.typed.Typed;

public class FailingDelegateParser extends DelegateParser {
	public FailingDelegateParser(Class<?> type) {
		super(type);
	}

	@Override
	public Object parse(String s, Typed typed) throws Exception {
		throw new CouldNotParseException(type, s);
	}
}
