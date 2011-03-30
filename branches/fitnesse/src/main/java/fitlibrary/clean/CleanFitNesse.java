/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.clean;

import java.io.File;

public class CleanFitNesse {
	public static void main(String[] args) {
		String diryName = "../fitnesse/FitNesseRoot";
		if (args.length > 0)
			diryName = args[0];
		cleanFitNesse(new File(diryName));
	}

	private static void cleanFitNesse(File diry) {
		File[] files = diry.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory())
				cleanFitNesse(file);
			else if (file.getName().endsWith(".zip")) {
				System.out.println("Deleted " + file.getAbsolutePath());
				file.delete();
			}
		}
	}
}
