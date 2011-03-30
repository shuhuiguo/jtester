/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;

public class FolderRunnerDifference implements DifferenceInterface {
	private static Logger logger = FitLibraryLogger.getLogger(FolderRunnerDifference.class);
	private static final String DOT_FILES = "files/";
	private static File CURRENT_TEST_FILE_DIRY = new File(".");

	public File getRelativeFile(String name) {
		// if (!name.startsWith("files/")) // Allow for implicit and explicit
		// reference
		// name = "files/"+name;
		File file = new File(CURRENT_TEST_FILE_DIRY, DOT_FILES + name);
		return file;
	}

	public File getHomeDirectory() {
		return new File(".");
	}

	public String url(File file) {
		return DOT_FILES + file.getName();
	}

	// public String trimFileName(String fileName) {
	// if (fileName.startsWith(DOT_FILES) || fileName.startsWith("files\\"))
	// return fileName.substring(DOT_FILES.length());
	// return fileName;
	// }
	public static void setCurrentTestDiryFile(File currentTestDiryFile) {
		CURRENT_TEST_FILE_DIRY = currentTestDiryFile;
	}

	// @Override
	public LocalFile getLocalFile(String localFileName) {
		logger.trace("getLocalFile(" + localFileName + ")");
		return new FolderRunnerLocalFile(localFileName);
	}

	// @Override
	public LocalFile getLocalFile(File file) {
		logger.trace("getLocalFile(File: " + file.getPath() + ")");
		return new FolderRunnerLocalFile(file);
	}

	// @Override
	public LocalFile getGlobalFile(File file) {
		logger.trace("getGlobalFile(" + file.getPath() + ")");
		return getLocalFile(file);
	}

	// @Override
	public LocalFile getGlobalFile(String fileName) {
		return new FolderRunnerGlobalFile(fileName);
	}

	// @Override
	public void setContext(File file) {
		logger.trace("setContext(" + file.getAbsolutePath() + ")");
		FolderRunnerLocalFile.setContext(file);
	}

	// @Override
	public boolean inFitNesse() {
		return false;
	}
}
