/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.specialAction;

import fitlibrary.exception.FitLibraryException;

public class SpecialActions {
	public int ten() {
		return 10;
	}
	public String html() {
	    return "<ul><li>ita<li>lics</ul>";
	}
	public boolean aTrueAction() {
		return true;
	}
	public boolean aFalseAction() {
		return false;
	}
	public boolean anErrorAction() {
		throw new FitLibraryException("whoops");
	}
	public int getIntProperty() {
	    return 2;
	}
	public String aStringWithWhiteSpace() {
		return "\tline one\n\tline\t2";
	}
}
