/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

public class FitLibraryRunnerDifference extends FolderRunnerDifference {
	public FitLibraryRunnerDifference(String fitNesseDirectoryPath) {
		FolderRunnerLocalFile.setContext(new File(fitNesseDirectoryPath + "/FitNesseRoot"));
	}
}
