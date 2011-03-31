/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/11/2006
*/

package fitlibrary.specify.domain;

@SuppressWarnings("unused")
public class SuperPrivateMethods {
	private int privatePropinSuper;

	private int getPrivatePropInSuper() {
		return privatePropinSuper;
	}
	private void setPrivatePropInSuper(int privatePropinSuper) {
		this.privatePropinSuper = privatePropinSuper;
	}
}
