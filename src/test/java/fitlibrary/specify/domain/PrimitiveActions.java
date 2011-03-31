/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 11/11/2006
*/

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;

public class PrimitiveActions implements DomainFixtured {
	private int anInt;
	
	public int doubleIt() {
		anInt = anInt + anInt;
		return anInt;
	}
	public int getAnInt() {
		return anInt;
	}
	public void setAnInt(int anInt) {
		this.anInt = anInt;
	}
}
