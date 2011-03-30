/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception;

/**
 * An exception that's ignored as the problem has already been signalled locally
 * in the table.
 */
public class IgnoredException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Exception ignoredException = null;

	public Exception getIgnoredException() {
		return ignoredException;
	}

	public IgnoredException(Exception ignoredException) {
		this.ignoredException = ignoredException;
	}

	public IgnoredException() {
		//
	}

	@Override
	public String toString() {
		return "Ignored: " + ignoredException;
	}
}
