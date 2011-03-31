/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fit.specify;

import fit.ActionFixture;
import fitlibrary.DoFixture;

public class ClearActionFixture extends DoFixture {
	public ClearActionFixture() {
		ActionFixture.actor = null;
	}
}
