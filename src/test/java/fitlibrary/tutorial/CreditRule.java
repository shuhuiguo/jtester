/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

import fitlibrary.traverse.function.Rule;

public class CreditRule implements Rule {
	private CreditApplication sut = new CreditApplication();
	private int monthsAsCustomer;
	private boolean hasPaidReliably;
	private double balanceOwing;

	public void setMonthsAsCustomer(int monthsAsCustomer) {
		this.monthsAsCustomer = monthsAsCustomer;
	}

	public void setHasPaidReliably(boolean hasPaidReliably) {
		this.hasPaidReliably = hasPaidReliably;
	}

	public void setBalanceOwing(double balanceOwing) {
		this.balanceOwing = balanceOwing;
	}

	public boolean getCreditIsAllowed() {
		return sut.creditPermitted(monthsAsCustomer, hasPaidReliably, balanceOwing);
	}

	public double getCreditLimit() {
		return sut.creditLimit(monthsAsCustomer, hasPaidReliably, balanceOwing);
	}
}
