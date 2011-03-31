/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

public class DiscountingApplication {
	public double expectedDiscount(double amount) {
		if (amount > 1000.00)
			return amount * 0.05;
		return 0.00;
	}
}
