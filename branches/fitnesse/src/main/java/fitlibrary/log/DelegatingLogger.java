/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.log;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

public class DelegatingLogger extends Logger {
	private LogDelegationConfig config;

	protected DelegatingLogger(String name, LogDelegationConfig config) {
		super(name);
		this.config = config;
	}

	@Override
	public void assertLog(boolean assertion, String msg) {
		super.assertLog(assertion, msg);
		if (config.isDelegating())
			delegate().assertLog(assertion, msg);
	}

	@Override
	public void debug(Object message) {
		super.debug(message);
		if (config.isDelegating())
			delegate().debug(message);
	}

	@Override
	public void debug(Object message, Throwable t) {
		super.debug(message, t);
		if (config.isDelegating())
			delegate().debug(message, t);
	}

	@Override
	public void error(Object message) {
		super.error(message);
		if (config.isDelegating())
			delegate().error(message);
	}

	@Override
	public void error(Object message, Throwable t) {
		super.error(message, t);
		if (config.isDelegating())
			delegate().error(message, t);
	}

	@Override
	public void fatal(Object message) {
		super.fatal(message);
		if (config.isDelegating())
			delegate().fatal(message);
	}

	@Override
	public void fatal(Object message, Throwable t) {
		super.fatal(message, t);
		if (config.isDelegating())
			delegate().fatal(message, t);
	}

	@Override
	public void info(Object message) {
		super.info(message);
		if (config.isDelegating())
			delegate().info(message);
	}

	@Override
	public void info(Object message, Throwable t) {
		super.info(message, t);
		if (config.isDelegating())
			delegate().info(message, t);
	}

	@Override
	public void log(Priority priority, Object message) {
		super.log(priority, message);
		if (config.isDelegating())
			delegate().log(priority, message);
	}

	@Override
	public void log(String callerFQCN, Priority level2, Object message, Throwable t) {
		super.log(callerFQCN, level2, message, t);
		if (config.isDelegating())
			delegate().log(callerFQCN, level2, message, t);
	}

	@Override
	public void trace(Object message) {
		super.trace(message);
		if (config.isDelegating())
			delegate().trace(message);
	}

	@Override
	public void trace(Object message, Throwable t) {
		super.trace(message, t);
		if (config.isDelegating())
			delegate().trace(message, t);
	}

	@Override
	public void warn(Object message) {
		super.warn(message);
		if (config.isDelegating())
			delegate().warn(message);
	}

	@Override
	public void warn(Object message, Throwable t) {
		super.warn(message, t);
		if (config.isDelegating())
			delegate().warn(message, t);
	}

	private Logger delegate() {
		return config.delegate();
	}
}
