/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception.parse;

public class CouldNotParseException extends ParseException {
	private static final long serialVersionUID = 1L;

	public CouldNotParseException(Class<?> type, String s) {
		super("Unable to parse \"" + s + "\" of type: " + type);
	}

	public CouldNotParseException(Class<?> type) {
		super("Unable to parse a string of type: " + type);
	}
}
