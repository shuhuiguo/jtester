/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.parser;

import fitlibrary.DoFixture;

public class UseToStringForChecking extends DoFixture {
	public Object useToString() {
		return new ClassWithNoTypeAdapter();
	}
	public static class ClassWithNoTypeAdapter {
		@Override
		public String toString() {
			return "77";
		}
	}
}
