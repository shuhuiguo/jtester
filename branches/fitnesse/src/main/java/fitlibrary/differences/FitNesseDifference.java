/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.differences;

import java.io.File;

/**
 * This manages the compile-time differences between running with FitNesse or in
 * batch
 */
public class FitNesseDifference implements DifferenceInterface {
	protected final static String LOCAL_FILES = "/files";
	protected final static String FITNESSE_FILES_LOCATION = "FitNesseRoot" + LOCAL_FILES;

	public File getRelativeFile(String name) {
		return new File(getHomeDirectory(), name);
	}

	public File getHomeDirectory() {
		File fitNesse = new File(FITNESSE_FILES_LOCATION);
		if (!fitNesse.exists() || !fitNesse.isDirectory())
			throw new RuntimeException("The FitNesse directories have changed: " + fitNesse.getAbsolutePath());
		return fitNesse;
	}

	public String url(String fileName) {
		return LOCAL_FILES + "/" + fileName;
	}

	// @Override
	public LocalFile getLocalFile(String fileName) {
		return new FitNesseLocalFile(fileName);
	}

	// @Override
	public LocalFile getLocalFile(File file) {
		return new FitNesseLocalFile(file);
	}

	// @Override
	public LocalFile getGlobalFile(File file) {
		return getLocalFile(file);
	}

	// @Override
	public LocalFile getGlobalFile(String fileName) {
		return getLocalFile(fileName);
	}

	// @Override
	public void setContext(File file) {
		throw new RuntimeException("This should never be called, as the FolderRunner calls it.");
	}

	// @Override
	public boolean inFitNesse() {
		return true;
	}
}
