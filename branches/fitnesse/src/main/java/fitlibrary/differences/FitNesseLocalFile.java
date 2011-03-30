/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

import fitlibrary.utility.StringUtility;

public class FitNesseLocalFile implements LocalFile {
	protected final static String LOCAL_FILES = "/files";
	protected static String FITNESSE_FILES_LOCATION = "FitNesseRoot" + LOCAL_FILES;
	private String fileName;

	public FitNesseLocalFile(String fileName) {
		setFileName(fileName);
	}

	public FitNesseLocalFile(File file) {
		String path = file.getPath();
		setFileName(StringUtility.replaceString(path, "\\", "/"));
	}

	private void setFileName(String fileName) {
		final String prefix = LOCAL_FILES + "/";
		if (fileName.startsWith(prefix))
			this.fileName = fileName.substring(prefix.length());
		else
			this.fileName = fileName;
	}

	// @Override
	public LocalFile withSuffix(String suffix) {
		String name = fileName;
		int last = fileName.lastIndexOf(".");
		if (last >= 0)
			name = name.substring(0, last + 1) + suffix;
		return new FitNesseLocalFile(name);
	}

	// @Override
	public File getFile() {
		if (fileName.startsWith("/") || (fileName.length() > 1 && fileName.charAt(1) == ':'))
			return new File(fileName);
		return new File(FITNESSE_FILES_LOCATION + "/" + fileName);
	}

	// @Override
	public void mkdirs() {
		File file = getFile().getParentFile();
		if (!file.exists())
			file.mkdirs();
	}

	// @Override
	public String htmlImageLink() {
		return "<img src=\"" + LOCAL_FILES + "/" + fileName + "\">";
	}

	// @Override
	public String htmlLink() {
		String name = fileName;
		int last = fileName.lastIndexOf("/");
		if (last >= 0)
			name = name.substring(last + 1);
		return "<a href=\"" + LOCAL_FILES + "/" + fileName + "\">" + name + "</a>";
	}

	public static void fitNessePrefix(String prefix) {
		FITNESSE_FILES_LOCATION = prefix + FITNESSE_FILES_LOCATION;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof FitNesseLocalFile))
			return false;
		return fileName.equals(((FitNesseLocalFile) object).fileName);
	}

	@Override
	public int hashCode() {
		return fileName.hashCode();
	}

	@Override
	public String toString() {
		return "FitNesseLocalFile[" + fileName + "]";
	}
}
