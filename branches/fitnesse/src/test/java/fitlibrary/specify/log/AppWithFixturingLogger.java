/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/

package fitlibrary.specify.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import fitlibrary.log.FixturingLogger;

public class AppWithFixturingLogger {
	private static Logger logger = FixturingLogger.getLogger(AppWithFixturingLogger.class);
	
	public boolean call() {
		logger.trace("App called");
		return true;
	}
	public void routeLogging() {
		Logger.getRootLogger().setLevel(Level.ALL);
		Logger.getRootLogger().addAppender(new ConsoleAppender(new SimpleLayout()));
		FixturingLogger.setDelegatingToNormalLogger(true);
	}
}
