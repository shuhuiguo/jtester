/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 16/11/2006
 */

package fitlibrary.parser.lookup;

import fitlibrary.parser.Parser;
import fitlibrary.traverse.Evaluator;
import fitlibrary.typed.Typed;

public interface ParserFactory {
	public Parser parser(Evaluator evaluator, Typed typed);
}
