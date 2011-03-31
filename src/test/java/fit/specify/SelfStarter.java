/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fit.specify;

import fitlibrary.specify.SelfStartingActionFixture;

public class SelfStarter extends SelfStartingActionFixture {
    private String s;

    public void enterString(String text) {
        this.s = text;
    }
    public String s() {
        return s;
    }
}
