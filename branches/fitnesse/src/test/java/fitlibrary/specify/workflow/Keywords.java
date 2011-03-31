/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify.workflow;

import java.awt.Point;

import fitlibrary.specify.DomainAdapterUnderTest;

public class Keywords {
	private double sum = 0.0;

	public void buyAtDollarWithDiscountPercent(int count, double cost, int discountPercent) {
		sum  += count * cost * (100-discountPercent)/100;
	}
	public double totalOwingDollar() {
		return sum;
	}
	public Point makeYourPoint(int x, int y) {
		return new Point(x,y);
	}
	public DomainAdapterUnderTest addObject() {
		return new DomainAdapterUnderTest();
	}
}
