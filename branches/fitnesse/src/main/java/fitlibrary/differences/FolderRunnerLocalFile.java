/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;
import fitlibrary.utility.StringUtility;

public class FolderRunnerLocalFile implements LocalFile {
	private static Logger logger = FitLibraryLogger.getLogger(FolderRunnerLocalFile.class);
	protected File file;
	private static File CONTEXT = new File(".");

	public FolderRunnerLocalFile(File file) {
		this.file = file;
	}

	public FolderRunnerLocalFile(String localFileNameInitial) {
		String localFileName = localFileNameInitial;
		if (localFileName.startsWith("/"))
			localFileName = localFileName.substring(1);
		if (!localFileName.startsWith("files/") && !localFileName.startsWith("files\\"))
			localFileName = "files/" + localFileName;
		this.file = new File(CONTEXT, localFileName);
		logger.trace("FolderRunnerLocalFile(" + localFileNameInitial + ")");
		logger.trace("FolderRunnerLocalFile(" + file.getAbsolutePath() + ") exists = " + file.exists());
	}

	// @Override
	public LocalFile withSuffix(String suffix) {
		String name = file.getPath();
		int last = name.lastIndexOf(".");
		if (last >= 0)
			name = name.substring(0, last + 1) + suffix;
		logger.trace("withSuffix(): " + name);
		return new FolderRunnerLocalFile(name);
	}

	// @Override
	public File getFile() {
		logger.trace("getFile(): " + file.getAbsolutePath());
		return file;
	}

	// @Override
	public void mkdirs() {
		File diry = file.getParentFile();
		logger.trace("mkdirs(): " + diry.getAbsolutePath());
		if (!diry.exists())
			diry.mkdirs();
	}

	// @Override
	public String htmlImageLink() {
		return "<img src=\"file:///" + escape(file.getPath()) + "\">";
	}

	// @Override
	public String htmlLink() {
		return "<a href=\"file:///" + escape(file.getPath()) + "\">" + file.getName() + "</a>";
	}

	protected String escape(String path) {
		return StringUtility.replaceString(path, "\\", "/").replaceAll(" ", "%20");
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof FolderRunnerLocalFile))
			return false;
		String absolutePath = ((FolderRunnerLocalFile) object).file.getPath();
		String otherAbsolutePath = file.getPath();
		boolean equals = absolutePath.equals(otherAbsolutePath);
		logger.trace(absolutePath + ".equals(" + otherAbsolutePath + ") = " + equals);
		return equals;
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}

	@Override
	public String toString() {
		return "FolderRunnerLocalFile[" + file.getName() + "]";
	}

	public static void setContext(File context) {
		CONTEXT = context;
	}
}
