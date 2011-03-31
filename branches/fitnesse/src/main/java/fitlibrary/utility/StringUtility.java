/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

public class StringUtility {
	// Simple String replace that can handle special chars like "\"
	public static String replaceString(final String textInitial, String pattern, String replacement) {
		String text = textInitial;
		int pos = 0;
		while (true) {
			pos = text.indexOf(pattern, pos);
			if (pos < 0)
				break;
			text = text.substring(0, pos) + replacement + text.substring(pos + pattern.length());
			pos += replacement.length();
		}
		return text;
	}
}
