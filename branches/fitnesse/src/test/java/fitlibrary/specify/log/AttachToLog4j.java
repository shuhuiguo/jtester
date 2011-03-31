/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.log;

public class AttachToLog4j {
	private AppWithLog4j app = new AppWithLog4j();
	public boolean callIntoApplication() {
		return app.call();
	}
}
