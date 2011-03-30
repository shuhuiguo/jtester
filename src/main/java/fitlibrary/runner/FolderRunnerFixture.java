/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.File;

import fitlibrary.DoFixture;
import fitlibrary.differences.DifferenceInterface;
import fitlibrary.log.Logging;
import fitlibrary.traverse.Traverse;
import fitlibrary.utility.StringUtility;

public class FolderRunnerFixture extends DoFixture {
	private static final String TESTFILES = "../";

	public void log() {
		Logging.setLogging(true);
	}

	public boolean runGiving(String testDirectoryName, String reportDirectoryName) throws Exception {
		DifferenceInterface previousDifferenceStrategy = Traverse.getDifferenceStrategy();
		try {
			long start = System.currentTimeMillis();
			Report run = new FolderRunner().run(TESTFILES + testDirectoryName, TESTFILES + reportDirectoryName);
			String theCounts = run.getCounts();
			long end = System.currentTimeMillis();
			// showAfterTable(urlFile(TESTFILES+reportDirectoryName+File.separator+"reportIndex.html","report"));
			showAfterTable(theCounts + " in " + (end - start) / 1000 + " seconds");
			return !run.failing();
		} finally {
			Traverse.setDifferenceStrategy(previousDifferenceStrategy);
		}
	}

	@SuppressWarnings("unused")
	private String urlFile(String fileName, String title) {
		String url = StringUtility.replaceString("file:///" + new File(fileName).getAbsolutePath(), " ", "%20");
		url = StringUtility.replaceString(url, "\\", "/");
		return "<a href=\"" + url + "\">" + title + url + "</a>";
	}
}
