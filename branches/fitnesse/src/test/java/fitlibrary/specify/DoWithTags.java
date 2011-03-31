/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.parser.tagged.TaggedString;

public class DoWithTags {
	public TaggedString taggedText() {
		return new TaggedString("<b>bold</b>");
	}
	public TaggedString tagText(TaggedString s) {
		return s;
	}
}
