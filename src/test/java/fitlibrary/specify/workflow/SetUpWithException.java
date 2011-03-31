/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.workflow;

import fitlibrary.traverse.DomainAdapter;

public class SetUpWithException implements DomainAdapter {
	public void setUp() {
		throw new RuntimeException();
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
