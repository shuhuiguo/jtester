/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import fitlibrary.object.DomainObjectCheckTraverse;

public class DomainObjectCheckFixture extends FitLibraryFixture {

	public DomainObjectCheckFixture(Object sut) {
		setSystemUnderTest(sut);
		DomainObjectCheckTraverse domainObjectCheckTraverse = new DomainObjectCheckTraverse(this);
		setTraverse(domainObjectCheckTraverse);
	}
}
