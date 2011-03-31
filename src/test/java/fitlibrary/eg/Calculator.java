/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 3/09/2006
*/

package fitlibrary.eg;

import fitlibrary.object.DomainFixtured;

public class Calculator implements DomainFixtured {
	private int total;

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int plus(int i) {
		total += i;
		return total;
	}
}
