/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.exception;

import fitlibrary.utility.MustBeThreadSafe;

public interface ExceptionHandling extends MustBeThreadSafe {
	String exceptionMessage(Throwable throwable);

	Throwable unwrapThrowable(Throwable throwable);

	boolean unwrappedIsShow(Exception e);
}
