/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.diff;

import java.util.LinkedList;

import fitlibrary.diff.Diff_match_patch.Diff;
import fitlibrary.tableOnParse.TableElementOnParse;

public class StringDifferencingStandard implements StringDifferencing {
	// @Override
	public void mustBeThreadSafe() {
		//
	}

	// @Override
	public String differences(String actual, String expected) {
		if (actual.length() < 6 || expected.length() < 6)
			return "";
		LinkedList<Diff> diffs = new Diff_match_patch().diff_main(actual, expected, true);
		StringBuilder s = new StringBuilder("<hr>");
		int sameCount = 0;
		int sameButTwoCount = 0;
		int sameMaxLength = 0;
		for (Diff diff : diffs) {
			String text = diff.text;
			int length = text.length();
			switch (diff.operation) {
			case DELETE:
				s.append("<strike>" + subst(text) + "</strike>");
				break;
			case EQUAL:
				s.append("<divv style='color:white'>" + text + "</divv>");
				sameCount += length;
				if (length <= 2)
					sameButTwoCount++;
				sameMaxLength = Math.max(sameMaxLength, length);
				break;
			case INSERT:
				s.append("<b>" + subst(text) + "</b>");
				break;
			}
		}
		String result = s.toString();
		if (result.contains("  ") || result.contains("&nbsp"))
			result = result.replace("  ", visibleSpace() + visibleSpace());
		else if (sameButTwoCount * 10 > sameCount && sameMaxLength < 3)
			return "";
		return result + TableElementOnParse.label("diff");
	}

	protected String visibleSpace() {
		return "&Delta;";
	}

	protected String subst(String sOriginal) {
		String s = sOriginal;
		if (s.startsWith(" "))
			s = visibleSpace() + s.substring(1);
		if (s.endsWith(" "))
			s = s.substring(0, s.length() - 1) + visibleSpace();
		return s;
	}
}
