/*
// * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.runner;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFiles {
	
	public static void copyFilesRecursively(File sourceFiles, File targetFiles, String diryName) throws IOException {
		if (!targetFiles.exists())
			targetFiles.mkdir();
		File targetDiry = new File(targetFiles,diryName);
		targetDiry.mkdir();
		copyFilesRecursively(new File(sourceFiles,diryName),targetDiry);
	}
	private static void copyFilesRecursively(File sourceDiry, File targetDiry) throws IOException {
		File[] files = sourceDiry.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			File target = new File(targetDiry,file.getName());
			if (file.isDirectory()) {
				target.mkdir();
				copyFilesRecursively(file,target);
			}
			else
				copyFile(file,target);
		}
	}
	private static void copyFile(File source, File target) throws IOException {
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(target));
		while (true) {
			int in = reader.read();
			if (in < 0)
				break;
			writer.write(in);
		}
		reader.close();
		writer.close();
	}

}
