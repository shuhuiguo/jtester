/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.missingProperty;

import fitlibrary.object.DomainFixtured;
import fitlibrary.traverse.DomainAdapter;

public class InDomainAdapter implements DomainAdapter, DomainFixtured {
	// @Override
	public Object getSystemUnderTest() {
		return new InJustSut();
	}
}
