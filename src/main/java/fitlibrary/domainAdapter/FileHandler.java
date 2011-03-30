/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.domainAdapter;

import java.io.File;

import fitlibrary.annotation.ShowSelectedActions;

@ShowSelectedActions
public class FileHandler extends AbstractFileHandler {
	public FileHandler(String fileName) {
		fileNameIs(fileName);
	}

	public void fileNameIs(String fileNameGiven) {
		file = new File(fileNameGiven);
	}
}