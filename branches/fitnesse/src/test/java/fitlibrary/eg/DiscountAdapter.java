/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.eg;

import fitlibrary.traverse.DomainAdapter;

public class DiscountAdapter implements DomainAdapter {
	private Discount discount = new Discount();

	// @Override
	public Object getSystemUnderTest() {
		return discount;
	}
}
