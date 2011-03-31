/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.log;

import org.apache.log4j.Logger;

import fitlibrary.log.FitLibraryLogger;
import fitlibrary.log.FixturingLogger;

public class AppWithLog4j {
	private static Logger logger = Logger.getLogger(AppWithLog4j.class);
	
	public boolean call() {
		logger.trace("App called");
		return true;
	}
	public void alsoShowFixturingInNormalLog(boolean delegate) {
		FixturingLogger.setDelegatingToNormalLogger(delegate);
	}
	public void alsoShowFitLibraryInNormalLog(boolean delegate) {
		FitLibraryLogger.setDelegatingToNormalLogger(delegate);
	}
}
