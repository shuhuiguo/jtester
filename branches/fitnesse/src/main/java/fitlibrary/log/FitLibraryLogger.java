/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.log;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.RootLogger;

public class FitLibraryLogger extends Logger {
	protected static boolean DELEGATING_TO_NORMAL_LOGGER = false;
	private static Hierarchy hierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
	private static LoggerFactory factory = new LoggerFactory() {
		// @Override
		public Logger makeNewLoggerInstance(final String name) {
			return new DelegatingLogger(name, new LogDelegationConfig() {
				// @Override
				public boolean isDelegating() {
					return DELEGATING_TO_NORMAL_LOGGER;
				}

				// @Override
				public Logger delegate() {
					return Logger.getLogger(name);
				}
			});
		}
	};

	protected FitLibraryLogger() {
		super("FitLibraryLogger");
	}

	public static Hierarchy getOwnHierarchy() {
		return hierarchy;
	}

	public static Logger getRootLogger() {
		return hierarchy.getRootLogger();
	}

	public static Logger getLogger(String name) {
		return hierarchy.getLogger(name, factory);
	}

	public static Logger getLogger(Class<?> type) {
		return getLogger(type.getName());
	}

	public static Logger exists(final String name) {
		return hierarchy.exists(name);
	}

	public static void setDelegatingToNormalLogger(boolean delegating) {
		DELEGATING_TO_NORMAL_LOGGER = delegating;
	}
}
