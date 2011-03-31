/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fitlibrary.exception.FitLibraryException;

public class FileIO {
	public static Iterator<File> filesIteratorWithSuffix(File diry, String suffix) {
		return filesWithSuffix(diry, suffix).iterator();
	}

	public static List<File> filesWithSuffix(File diry, String suffix) {
		List<File> accumulatedFiles = new ArrayList<File>();
		injectFile(diry, suffix, accumulatedFiles);
		return accumulatedFiles;
	}

	private static void injectFile(File diry, final String suffix, List<File> accumulatedFiles) {
		File[] files = diry.listFiles();
		if (files == null)
			throw new FitLibraryException("Not a directory: " + diry);
		for (File f : files) {
			if (f.getName().endsWith(suffix))
				accumulatedFiles.add(f);
			if (f.isDirectory())
				injectFile(f, suffix, accumulatedFiles);
		}
	}

	public static String read(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		StringBuilder stringBuilder = new StringBuilder();
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		reader.close();
		return stringBuilder.toString();
	}
}
