/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.PrimitiveArrayFixture;

public class PrimitiveArrayFixtureWithCollection extends PrimitiveArrayFixture {

    public PrimitiveArrayFixtureWithCollection() {
        super(new Object[]{new C("one"),new C("two"),new C("three")});
    }
    public static class C {
        private String s;

        public C(String s) {
            this.s = s;
        }
        @Override
		public String toString() {
            return s;
        }
    }
}
