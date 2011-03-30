/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

public class FolderRunnerGlobalFile extends FolderRunnerLocalFile {

	public FolderRunnerGlobalFile(File file) {
		super(file);
	}

	public FolderRunnerGlobalFile(String fileName) {
		super(fileName);
	}

	@Override
	public String htmlImageLink() {
		return "<img src=\"file:///" + escape(file.getAbsolutePath()) + "\">";
	}

	@Override
	public String htmlLink() {
		return "<a href=\"file:///" + escape(file.getAbsolutePath()) + "\">" + file.getName() + "</a>";
	}
}
