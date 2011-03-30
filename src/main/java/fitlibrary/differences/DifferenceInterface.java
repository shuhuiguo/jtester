/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

public interface DifferenceInterface {
	LocalFile getLocalFile(String localFileName);

	LocalFile getLocalFile(File file);

	LocalFile getGlobalFile(File file);

	LocalFile getGlobalFile(String fileName);

	void setContext(File file);

	boolean inFitNesse();
}
