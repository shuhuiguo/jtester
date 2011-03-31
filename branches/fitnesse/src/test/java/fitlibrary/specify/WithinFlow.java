/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fitlibrary.DoFixture;

public class WithinFlow extends DoFixture {
    public DoFixtureSetUp withSetUp() {
        return new DoFixtureSetUp();
    }
    public DoFixtureTearDown withTearDown() {
        return new DoFixtureTearDown();
    }
    public DoFixtureSetUpWithException withSetUpException() {
        return new DoFixtureSetUpWithException();
    }
}
