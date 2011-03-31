/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify.constraint;

import fitlibrary.traverse.DomainAdapter;

public class SetUpAndTearDownCalled implements DomainAdapter {
	private boolean isSetUp = false;

	public void setUp() {
		isSetUp = true;
	}

	// Throw an exception so we can tell for sure that tearDown() has been
	// called automatically.
	public void tearDown() {
		throw new RuntimeException("tear down");
	}

	// This constraint only suceeds if setUp() has been called automatically.
	public boolean aB(int a, int b) {
		return isSetUp && a < b;
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
