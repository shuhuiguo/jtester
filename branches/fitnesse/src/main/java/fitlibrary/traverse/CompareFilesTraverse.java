/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fitlibrary.parser.tree.ListTree;
import fitlibrary.parser.tree.Tree;
import fitlibrary.traverse.workflow.DoTraverse;

public class CompareFilesTraverse extends DoTraverse {
	public static final ListTree OK = new ListTree("OK");

	public CompareFilesTraverse(Object sut) {
		super(sut);
	}

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
		return dirySameAs(Traverse.getLocalFile(diryName1).getFile(), Traverse.getLocalFile(diryName2).getFile());
	}

	/**
	 * Returns OK if the two files match. Otherwise it returns an error message
	 * for the mismatch.
	 */
	public ListTree fileSameAs(String fileName1, String fileName2) throws IOException {
		return filesSameAs(Traverse.getLocalFile(fileName1).getFile(), Traverse.getLocalFile(fileName2).getFile());
	}

	public ListTree filesSameAs(File file1, File file2) throws IOException {
		if (file1.isDirectory()) {
			if (file2.isDirectory())
				return dirySameAs(file1, file2);
			return error(file1, "One is a folder");
		}
		return filesSame(file1, file2);
	}

	private ListTree dirySameAs(File diry1, File diry2) throws IOException {
		if (!diry1.isDirectory() && !diry2.isDirectory())
			return filesSameAs(diry1, diry2);
		if (!diry1.isDirectory())
			return error(diry1, "Must be a folder");
		if (!diry2.isDirectory())
			return error(diry2, "Must be a folder");
		return dirySame(diry1, diry2);
	}

	private ListTree dirySame(File diry1, File diry2) throws IOException {
		List<ListTree> children = new ArrayList<ListTree>();
		File[] files2 = diry2.listFiles();
		Map<String, File> fileMap = new HashMap<String, File>();
		for (int i = 0; i < files2.length; i++) {
			File f2 = files2[i];
			if (!f2.getName().startsWith("."))
				fileMap.put(f2.getName(), f2);
		}
		File[] files1 = diry1.listFiles();
		Arrays.sort(files1);
		for (int i = 0; i < files1.length; i++) {
			File f1 = files1[i];
			if (f1.getName().startsWith("."))
				continue;
			File f2 = fileMap.get(f1.getName());
			if (f2 == null) {
				if (f1.isDirectory())
					children.add(error(f1, "Missing folder"));
				else
					children.add(error(f1, "Missing"));
			} else if (f1.isDirectory() && f2.isDirectory())
				children.add(dirySame(f1, f2));
			else if (!f1.isDirectory() && !f2.isDirectory()) {
				ListTree filesSameAs = filesSameAs(f1, f2);
				if (filesSameAs == OK)
					children.add(new ListTree(Traverse.htmlLink(f1)));
				else {
					children.add(filesSameAs);
				}
			} else
				children.add(error(f1, f2, "Can't compare a folder with a file"));
			if (f2 != null)
				fileMap.remove(f2.getName());
		}
		for (String key : fileMap.keySet()) {
			File f = fileMap.get(key);
			if (f.isDirectory())
				children.add(error(f, "Surplus folder"));
			else
				children.add(error(f, "Surplus"));
		}
		for (Iterator<ListTree> it = children.iterator(); it.hasNext();) {
			if (it.next() instanceof ListTreeError)
				return new ListTreeError(Traverse.htmlLink(diry1), children);
		}
		return new ListTree(Traverse.htmlLink(diry1));
	}

	private ListTree filesSame(File file1, File file2) throws IOException {
		if (!file1.exists())
			return error(file1, "File doesn't exist");
		if (!file2.exists())
			return error(file2, "File doesn't exist");
		if (!file1.canRead())
			return error(file1, "File may be locked");
		if (!file2.canRead())
			return error(file2, "File may be locked");
		long lengthDifference = file2.length() - file1.length();
		if (lengthDifference > 0)
			return error(file1, file2, "File shorter by " + lengthDifference + " bytes than:");
		if (lengthDifference < 0)
			return error(file1, file2, "File longer by " + (-lengthDifference) + " bytes than:");
		return compareFiles(file1, file2);
	}

	private ListTree compareFiles(File file1, File file2) throws IOException {
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
		int byteNo = 0;
		try {
			while (true) {
				int read1 = reader1.read();
				if (read1 < 0)
					break;
				if (read1 != reader2.read())
					return error(file1, file2, "Files differ at byte position " + byteNo);
				byteNo++;
			}
		} finally {
			reader1.close();
			reader2.close();
		}
		return OK;
	}

	private ListTree error(File file, String errorMessage) {
		return new ListTreeError(Traverse.htmlLink(file), new Tree[] { new ListTree("<i>" + errorMessage + "</i>") });
	}

	private ListTree error(File file1, File file2, String errorMessage) {
		ListTree error = error(file1, errorMessage);
		error.addChild(new ListTree(Traverse.htmlLink(file2)));
		return error;
	}

	private static class ListTreeError extends ListTree {
		public ListTreeError(String message, Tree[] trees) {
			super(message, trees);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ListTreeError(String name, List children) {
			super(name, children);
		}
	}

	public static boolean failingTree(ListTree tree) {
		return tree instanceof ListTreeError;
	}

}
