/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.parse;

public class InvalidMapString extends ParseException {
	private static final long serialVersionUID = 1L;

	public InvalidMapString(String s) {
		super("Invalid String for mapping: " + s);
	}
}
