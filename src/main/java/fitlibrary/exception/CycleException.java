/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.exception;

public class CycleException extends FitLibraryExceptionWithHelp {
	private static final long serialVersionUID = 1L;

	public CycleException(String cycle, Object sut, Object domainObject) {
		super(cycle + " cycle between " + sut.getClass().getName() + " and " + domainObject.getClass().getName(),
				"SutCycle");
	}

}
