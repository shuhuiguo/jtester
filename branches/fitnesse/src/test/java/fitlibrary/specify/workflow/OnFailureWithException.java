/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.specify.workflow;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.traverse.DomainAdapter;

public class OnFailureWithException implements DomainAdapter {
	public boolean result() {
		return true;
	}

	public void end() {
		//
	}

	public void onFailure() {
		throw new FitLibraryException("onFailure() called");
	}

	// @Override
	public Object getSystemUnderTest() {
		return null;
	}
}
