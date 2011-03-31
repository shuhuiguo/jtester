/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.mapTraverse;

import java.util.HashMap;
import java.util.Map;

import fitlibrary.object.DomainFixtured;

@SuppressWarnings("rawtypes")
public class ErrorMap implements DomainFixtured {
	@SuppressWarnings("unchecked")
	public Map getErrorMap() {
		HashMap map = new HashMap();
		map.put("a",new InError());
		return map;
	}
	public static class InError {
		public static InError parse(String s) {
			throw new RuntimeException(s);
		}
		@Override
		public String toString() {
			return "InError[]";
		}
	}
}
