/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 20/08/2006
 */

package fitlibrary.specify.exception;

public class ExceptionThrownByNestedParse {
	public Value value() {
		return new Value();
	}

	public static class Value {
		public Colour getColour() {
			return new Colour();
		}
	}

	public static class Colour {
		public static Colour parse(String s) {
			throw new ForcedException();
		}
	}
}
