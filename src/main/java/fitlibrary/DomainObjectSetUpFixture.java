/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.object.DomainObjectSetUpTraverse;

public class DomainObjectSetUpFixture extends FitLibraryFixture {
	public DomainObjectSetUpFixture(Object sut) {
		setSystemUnderTest(sut);
		DomainObjectSetUpTraverse domainObjectSetUpTraverse = new DomainObjectSetUpTraverse(this);
		setTraverse(domainObjectSetUpTraverse);
	}
}
