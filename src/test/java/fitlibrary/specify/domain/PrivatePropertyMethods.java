/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 17/11/2006
*/

package fitlibrary.specify.domain;

import fitlibrary.object.DomainFixtured;

@SuppressWarnings("unused")
public class PrivatePropertyMethods extends SuperPrivateMethods implements DomainFixtured {
	private int privateProp;

	private int getPrivateProp() {
		return privateProp;
	}
	private void setPrivateProp(int privateProp) {
		this.privateProp = privateProp;
	}
}
