/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runner;

import java.io.File;

import fit.Parse;
import fitlibrary.utility.ParseUtility;

public class FileParseUtilities {
	public static boolean fileIsLocked(File file) {
		return file.exists() && !file.canWrite();
	}

	public static boolean isHtmlFileName(String name) {
		String upperName = name.toUpperCase();
		return upperName.endsWith(".HTML") || upperName.endsWith(".HTM");
	}

	public static boolean isXlsFileName(String name) {
		return name.toUpperCase().endsWith(".XLS");
	}

	public static void ignoreFile(File file, Exception e) {
		System.out.println("Ignored file: " + file.getAbsolutePath() + " due to: " + e);
	}

	public static String reportName(File file) {
		return ReportHtml.reportName(file);
	}

	public static Parse copyParse(Parse parse) {
		return ParseUtility.copyParse(parse);
	}
}
