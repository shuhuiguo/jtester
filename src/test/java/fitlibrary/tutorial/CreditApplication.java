/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

public class CreditApplication {
	public boolean creditPermitted(int monthsAsCustomer, boolean hasPaidReliably, double balanceOwing) {
		return monthsAsCustomer > 12 && hasPaidReliably && balanceOwing < 6000.0;
	}

	public double creditLimit(int monthsAsCustomer, boolean hasPaidReliably, double balanceOwing) {
		if (creditPermitted(monthsAsCustomer, hasPaidReliably, balanceOwing))
			return 1000.0;
		return 0.00;
	}
}
