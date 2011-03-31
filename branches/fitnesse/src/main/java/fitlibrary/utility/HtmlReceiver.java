/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.io.File;

public interface HtmlReceiver {
	void take(File file, String html);
}
