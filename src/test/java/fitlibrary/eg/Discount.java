/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.eg;

public class Discount {
	public double discountDollar(double dollar) {
		if (dollar <= 1000)
			return 0;
		return dollar * 0.05;
	}
}
