/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fitlibrary.exception.FitLibraryException;

public class CopyFilesFromJar {
	// Copy them into the files directory so they will be copied with anything
	// else there into the reports directory
	public static void copyCssAndImageFilesFromJar(File theInDiry) throws IOException {
		File filesDiry = new File(theInDiry, FolderRunner.FILES);
		if (!filesDiry.exists())
			filesDiry.mkdir();
		File cssDiry = new File(filesDiry, "css");
		if (!cssDiry.exists())
			cssDiry.mkdir();
		createFileIfNeeded(new File(cssDiry, "fitnesse.css"), "css/fitnesse_base.css");

		File imagesDiry = new File(filesDiry, "images");
		if (!imagesDiry.exists())
			imagesDiry.mkdir();
		createFileIfNeeded(new File(imagesDiry, "collapsableClosed.gif"), "images/collapsableClosed.gif");
		createFileIfNeeded(new File(imagesDiry, "collapsableOpen.gif"), "images/collapsableOpen.gif");
	}

	private static void createFileIfNeeded(File target, String resource) throws FileNotFoundException, IOException {
		if (target.exists())
			return;
		String fullResource = "Resources/FitNesseRoot/files/" + resource;
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		InputStream reader = classLoader.getResourceAsStream(fullResource);
		if (reader == null)
			throw new FitLibraryException("Unable to access resource from jar: " + fullResource);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(target));
		while (true) {
			int ch = reader.read();
			if (ch < 0)
				break;
			writer.write(ch);
		}
		reader.close();
		writer.close();
	}

}
