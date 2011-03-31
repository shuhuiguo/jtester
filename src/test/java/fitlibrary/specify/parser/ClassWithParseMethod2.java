/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.parser;

import fitlibrary.DoFixture;

public class ClassWithParseMethod2 extends DoFixture {
	public MyClass myClass() {
	    return new MyClass(3);
	}
	public MyClass sameMyClass(MyClass value) {
		return value;
	}
	public MyClass myClassPlus(MyClass one, MyClass two) {
		return one.plus(two);
	}
	public static class MyClass {
        private int i;

        public MyClass(int i) {
            this.i = i;
        }
        public MyClass plus(MyClass two) {
			return new MyClass(i+two.i);
		}
		public static MyClass parse(String s) {
			if (s.startsWith("i "))
				return new MyClass(Integer.parseInt(s.substring(2)));
			throw new RuntimeException("Invalid value: must start with 'i '");
	    }
        @Override
		public String toString() {
            return "i "+i;
        }
        @Override
		public boolean equals(Object object) {
            if (!(object instanceof MyClass))
                return false;
            return ((MyClass)object).i == i;
        }
		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
}
