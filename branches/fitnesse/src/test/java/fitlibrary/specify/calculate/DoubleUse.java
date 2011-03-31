/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 3/11/2006
*/

package fitlibrary.specify.calculate;

import fitlibrary.CalculateFixture;

public class DoubleUse {
	private CalculateFixture calc = new LocalCalculateFixture();

	public CalculateFixture calculating() {
		return calc ;
	}
	
	public static class LocalCalculateFixture extends CalculateFixture {
		public int cAB(int a, int b) { 
			return a+b;
		}
	}
}
