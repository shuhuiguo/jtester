/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.tutorial;

import fitlibrary.traverse.function.Rule;

public class CreditLimitRule implements Rule {
	private boolean creditIsAllowed;

	public void setCreditIsAllowed(boolean creditIsAllowed) {
		this.creditIsAllowed = creditIsAllowed;
	}

	public double getCreditLimit() {
		if (creditIsAllowed)
			return 1000.00;
		return 0.00;
	}
}