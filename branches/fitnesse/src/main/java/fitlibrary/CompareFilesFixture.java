/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary;

import java.io.File;
import java.io.IOException;

import fitlibrary.parser.tree.ListTree;
import fitlibrary.traverse.CompareFilesTraverse;

/**
 * Compare files and directories
 * 
 * See the FixtureFixture specifications for examples
 */
public class CompareFilesFixture {
	private CompareFilesTraverse compareFiles = new CompareFilesTraverse(this);

	/**
	 * Returns OK if the two directories contain equal directories and files.
	 * Otherwise it returns an error message for the mismatches.
	 */
	public ListTree directorySameAs(String diryName1, String diryName2) throws IOException {
		return folderSameAs(diryName1, diryName2);
	}

	/**
	 * Returns OK if the two directories contain equal directories and files.
	 * Otherwise it returns an error message for the mismatches.
	 */
	public ListTree folderSameAs(String diryName1, String diryName2) throws IOException {
		return compareFiles.folderSameAs(diryName1, diryName2);
	}

	/**
	 * Returns OK if the two files match. Otherwise it returns an error message
	 * for the mismatch.
	 */
	public ListTree fileSameAs(String fileName1, String fileName2) throws IOException {
		return compareFiles.fileSameAs(fileName1, fileName2);
	}

	public ListTree filesSameAs(File file1, File file2) throws IOException {
		return compareFiles.filesSameAs(file1, file2);
	}
}
