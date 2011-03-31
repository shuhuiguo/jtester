/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.missingMethod;

import fitlibrary.specify.missingMethod.ShowInJustSut.Colour;
import fitlibrary.traverse.DomainAdapter;

public class ShowInDomainAdapter implements DomainAdapter {
	// @Override
	public Object getSystemUnderTest() {
		return new ShowInJustSut();
	}

	public Colour findColour(String key) {
		// Returns a new one that won't match red
		return new Colour(key);
	}
}