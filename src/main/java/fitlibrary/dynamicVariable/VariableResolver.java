/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.dynamicVariable;

import fitlibrary.table.Tables;
import fitlibrary.utility.Pair;

public interface VariableResolver {
	Pair<String, Tables> resolve(String key);
}
