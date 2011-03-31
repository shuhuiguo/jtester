/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.utility;

import java.lang.reflect.InvocationTargetException;

public class ExceptionHandler {
	public static Throwable unwrap(Throwable throwable) {
		Throwable exception = throwable;
		while (true) {
			if (exception.getCause() != null)
				exception = exception.getCause();
			else if (exception.getClass().equals(InvocationTargetException.class))
				exception = ((InvocationTargetException) exception).getTargetException();
			else
				return exception;
		}
	}
}
