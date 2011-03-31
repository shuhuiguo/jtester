/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.specify.workflow;

import fitlibrary.traverse.DomainAdapter;

public class OnFailureWithResult implements DomainAdapter {
	public boolean result() {
		return true;
	}

	public void end() {
		//
	}

	public String onFailure() {
		return "onFailure() called";
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
