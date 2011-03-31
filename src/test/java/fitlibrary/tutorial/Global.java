/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

import fitlibrary.traverse.function.Rule;
import fitlibrary.tutorial.chat.Chat;

public class Global {
	public Rule a5PercentDiscountIsProvidedWheneverTheTotalPurchaseIsGreaterThanDollar1Comma000() {
		return new DiscountRule();
	}

	public Rule creditIsAllowedForWorthyCustomers() {
		return new CreditRule();
	}

	public Rule creditLimitDependsOnWhetherCreditIsAllowed() {
		return new CreditLimitRule();
	}

	public Rule creditIsAllowedForWorthyCustomersWhoHaveBeenTradingWithUsForMonths(int months) {
		CreditRule creditLimitRule = new CreditRule();
		creditLimitRule.setMonthsAsCustomer(months);
		return creditLimitRule;
	}

	public Chat withChat() {
		return new Chat();
	}

	public Calculator withACalculator() {
		return new Calculator();
	}
}
