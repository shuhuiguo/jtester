/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

import fitlibrary.utility.StringUtility;

public class FitNesseGlobalFile implements LocalFile {
	protected final static String FITNESSE_ROOT = "FitNesseRoot";
	private String fileName;
	private boolean absolute = false;

	public FitNesseGlobalFile(File file) {
		String path = file.getPath();
		if (path.startsWith(FITNESSE_ROOT)) {
			fileName = path.substring(FITNESSE_ROOT.length());
			fileName = StringUtility.replaceString(fileName, "\\", "/");
		} else {
			fileName = file.getAbsolutePath();
			absolute = true;
		}
	}

	// @Override
	public LocalFile withSuffix(String s) {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public File getFile() {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public void mkdirs() {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public String htmlImageLink() {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public String htmlLink() {
		if (absolute)
			return "<a href=\"file://" + fileName + "\">" + fileName + "</a>";
		String name = fileName;
		int last = fileName.lastIndexOf("/");
		if (last >= 0)
			name = name.substring(last + 1);
		return "<a href=\"http://localhost" + fileName + "\">" + name + "</a>";
	}
}
