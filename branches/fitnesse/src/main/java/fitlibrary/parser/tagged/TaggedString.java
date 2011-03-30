/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.tagged;

public class TaggedString {
	private String s;

	public TaggedString(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof TaggedString))
			return false;
		return s.equals(((TaggedString) object).s);
	}

	@Override
	public int hashCode() {
		return s.hashCode();
	}
}
