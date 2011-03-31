/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.workflow;

import fitlibrary.traverse.DomainAdapter;

public class TearDown implements DomainAdapter {
	public void anException() {
		throw new RuntimeException();
	}

	public boolean someAction() {
		return true;
	}

	public void tearDown() {
		throw new RuntimeException();
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
