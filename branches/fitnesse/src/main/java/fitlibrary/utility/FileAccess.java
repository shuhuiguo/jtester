/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.io.File;
import java.util.Iterator;

public interface FileAccess {
	Iterator<File> filesWithSuffix(String string);

	NullIterator<String> linesOf(File file);
}
