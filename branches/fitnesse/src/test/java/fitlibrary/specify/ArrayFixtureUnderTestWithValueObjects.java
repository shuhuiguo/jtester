/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.ArrayFixture;
import fitlibrary.specify.parser.ClassWithParseMethod2;

public class ArrayFixtureUnderTestWithValueObjects extends ArrayFixture {
	public ArrayFixtureUnderTestWithValueObjects() throws Exception {
		super(new Object[]{
    			new Pair(1,2),
				new Pair(3,4),
				new Pair(5,6)
				});
	}
	public static class Pair {
		private ClassWithParseMethod2.MyClass one, two;
		
		public Pair(int i, int j) {
			this.one = new ClassWithParseMethod2.MyClass(i);
			this.two = new ClassWithParseMethod2.MyClass(j);
		}
		public ClassWithParseMethod2.MyClass getOne() {
			return one;
		}
		public ClassWithParseMethod2.MyClass getTwo() {
			return two;
		}
	}
}
