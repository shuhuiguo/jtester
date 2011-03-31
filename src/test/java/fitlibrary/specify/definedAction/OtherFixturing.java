/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.definedAction;

public class OtherFixturing {
	private int value = 0;
	
	public OtherFixturing() {
		//
	}
	public OtherFixturing(int value) {
		this.value = value;
	}
	public int value() {
		return value;
	}
	public OtherFixturing other(int i) {
		return new OtherFixturing(i);
	}
}
