/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.workflow;

public class TextPassed {
	public boolean isSeveralLines(String s) {
		System.out.println("'"+s+"'");
		return s.equals("abc\ndef");
	}
	public boolean isSeveralLinesWithNewlines(String s) {
		return s.equals("\nabc\ndef\n");
	}
	public boolean isSeveralLinesWithoutNewline(String s) {
		return s.equals("abcdef");
	}
	public boolean hasTags(String s) {
		return s.equals("<a>A<b/></a>");
	}
}
