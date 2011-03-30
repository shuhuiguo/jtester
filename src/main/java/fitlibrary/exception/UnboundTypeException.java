/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 11/11/2006
 */

package fitlibrary.exception;

import java.lang.reflect.Type;

public class UnboundTypeException extends FitLibraryException {
	private static final long serialVersionUID = 1L;

	public UnboundTypeException(Type unboundType, String context) {
		super("Type variable " + unboundType + " is unbound " + context);
	}
}
