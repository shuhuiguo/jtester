/*
 * @author Rick Mugridge 2005/01/05
 *
 * Copyright (c) 2005 Rick Mugridge, University of Auckland, NZ
 * Released under the terms of the GNU General Public License version 2 or later.
 *
 */
package fitbook;

import fitlibrary.DoFixture;
import fitlibrary.suite.SuiteFixture;

public class ChatSuiteFixture extends SuiteFixture {
    public DoFixture chat() {
        return new ChatStart();
    }
    public DoFixture anotherChat() {
        return new ChatStart();
    }
}
