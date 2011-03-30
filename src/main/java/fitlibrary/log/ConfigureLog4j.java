/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.log;

import org.apache.log4j.Logger;

import fitlibrary.runtime.RuntimeContextInternal;

public class ConfigureLog4j {
	private final RuntimeContextInternal runtime;
	private final ShowAfterTableAppender appender;
	private final ConfigureLogger configureNormalLog4j;
	private final ConfigureLogger configureFitLibraryLogger;
	private final ConfigureLogger configureFixturingLogger;
	private ConfigureLogger currentLogger;

	public ConfigureLog4j(RuntimeContextInternal runtime) {
		this.runtime = runtime;
		this.appender = new ShowAfterTableAppender(runtime, new CustomHtmlLayout());
		this.configureNormalLog4j = new ConfigureLogger(appender) {
			@Override
			protected Logger rootLogger() {
				return Logger.getRootLogger();
			}

			@Override
			protected Logger getLogger(String name) {
				return Logger.getLogger(name);
			}
		};
		this.configureFitLibraryLogger = new ConfigureLogger(appender) {
			@Override
			protected Logger rootLogger() {
				return FitLibraryLogger.getRootLogger();
			}

			@Override
			protected Logger getLogger(String name) {
				return FitLibraryLogger.getLogger(name);
			}
		};
		this.configureFixturingLogger = new ConfigureLogger(appender) {
			@Override
			protected Logger rootLogger() {
				return FixturingLogger.getRootLogger();
			}

			@Override
			protected Logger getLogger(String name) {
				return FixturingLogger.getLogger(name);
			}
		};
		this.currentLogger = configureFixturingLogger;
	}

	public ConfigureLogger withNormalLog4j() {
		return configureNormalLog4j;
	}

	public ConfigureLogger withFitLibraryLogger() {
		return configureFitLibraryLogger;
	}

	public ConfigureLogger withFixturingLogger() {
		return configureFixturingLogger;
	}

	public void log(String s) {
		currentLogger.getLogger(runtime.getDefinedActionCallManager().topName()).debug(s);
	}
}
