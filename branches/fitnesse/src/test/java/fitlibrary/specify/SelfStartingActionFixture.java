/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fit.Parse;

public class SelfStartingActionFixture extends fit.ActionFixture {
    @Override
	public void doTable(Parse table) {
        actor = this;
        super.doTable(table);
    }
}
