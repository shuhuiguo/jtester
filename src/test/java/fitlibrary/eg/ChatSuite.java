/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 1/09/2006
*/

package fitlibrary.eg;

import fitlibrary.eg.chat.ChatSystem;
import fitlibrary.suite.SuiteFixture;
import fitlibrary.traverse.FitLibrarySelector;

public class ChatSuite extends SuiteFixture {
	public Object chat() {
		return FitLibrarySelector.selectWorkflow(new ChatSystem());
	}
	public Object anotherChat() {
		return chat();
	}
}
