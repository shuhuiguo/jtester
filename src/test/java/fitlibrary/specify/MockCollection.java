/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

public class MockCollection {
	public int plus = 0;
	public String ampersand;
	
	public MockCollection(int plus, String ampersand) {
		this.plus = plus;
		this.ampersand = ampersand;
	}
	public int getProp() {
	    return plus;
	}
	public String getAmpersand() {
		return ampersand;
	}
	public int getPlus() {
		return plus;
	}
}
