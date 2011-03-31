/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/08/2006
 */

package fitlibrary.specify.valueObject;

import fitlibrary.parser.lookup.ParseDelegation;
import fitlibrary.specify.eg.FixedPoint;
import fitlibrary.specify.eg.FixedPointInterface;
import fitlibrary.traverse.FitLibrarySelector;
import fitlibrary.traverse.Traverse;

public class ParseMyFixedPointAsStringWithSuperDelegate {
	public ParseMyFixedPointAsStringWithSuperDelegate() {
		ParseDelegation.registerSuperParseDelegate(FixedPointInterface.class, new FixedPointSuperDelegate());
	}

	public FixedPoint aFixedPoint(FixedPoint point) {
		return point;
	}

	public Traverse aFixedPointAsDomainObject(FixedPoint point) {
		return FitLibrarySelector.selectDomainCheck(point);
	}

	public static class FixedPointSuperDelegate {
		public Object parse(String s, Class<?> type) {
			if (!s.startsWith("(") || !s.endsWith(")"))
				throw new RuntimeException("Badly formatted point");
			int comma = s.indexOf(",");
			if (comma < 0)
				throw new RuntimeException("Badly formatted point");
			return new FixedPoint(Integer.parseInt(s.substring(1, comma)), Integer.parseInt(s.substring(comma + 1,
					s.length() - 1)));
		}

		public boolean matches(FixedPointInterface a, FixedPointInterface b) {
			if (a == null)
				return b == null;
			return a.getX() == b.getX();
		}

		public String show(Object object) {
			if (object == null)
				return "null";
			return "point: " + object.toString();
		}
	}
}
