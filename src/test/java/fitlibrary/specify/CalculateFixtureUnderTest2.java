/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.CalculateFixture;
import fitlibrary.DoFixture;

public class CalculateFixtureUnderTest2 extends DoFixture {
	public Calculate calc() {
		return new Calculate();
	}
	public static class Calculate extends CalculateFixture {
		public Calculate() {
			setRepeatString("\"");
			setExceptionString("exception");
		}
		public int plusAB(int a, int b) {
			return a + b;
		}
		public String exceptionMethod() {
			throw new RuntimeException("Expected exception");
		}
	}
}
