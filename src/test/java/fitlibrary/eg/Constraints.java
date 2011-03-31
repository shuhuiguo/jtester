/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

public class Constraints {
	public boolean aB(int a, int b) {
		return a < b;
	}
	public boolean positive(int a) {
		return a > 0;
	}
}
