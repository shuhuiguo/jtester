/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import fitlibrary.runtime.RuntimeContextInternal;

public class ShowAfterTableAppender extends AppenderSkeleton {
	private RuntimeContextInternal runtime;

	public ShowAfterTableAppender(RuntimeContextInternal runtime, Layout layout) {
		this.runtime = runtime;
		this.layout = layout;
	}

	@Override
	protected void append(LoggingEvent event) {
		String s = layout.format(event);
		runtime.showAsAfterTable("Logging", s);
	}

	// @Override
	public void close() {
		//
	}

	// @Override
	public boolean requiresLayout() {
		return false;
	}
}