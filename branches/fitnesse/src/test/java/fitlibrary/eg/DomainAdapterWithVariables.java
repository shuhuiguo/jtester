/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.traverse.DomainAdapter;

public abstract class DomainAdapterWithVariables implements DomainAdapter {
	protected Map<String,Variable> variables = new HashMap<String,Variable>();

	public Object findVariable(String s) {
		Variable var = variables.get(s);
		if (var == null) {
			var = new Variable(s);
			variables.put(s,var);
		}
		return var;
	}
}
