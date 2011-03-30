/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * 20/10/2009
 */

package fitlibrary.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import fit.Fixture;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.utility.ExceptionHandler;
import fitlibrary.utility.HtmlUtils;

public class ExceptionHandlingStandard implements ExceptionHandling {
	// @Override
	public void mustBeThreadSafe() {
		//
	}

	// @Override
	public String exceptionMessage(Throwable throwable) {
		Throwable exception = unwrapThrowable(throwable);
		if (!BatchFitLibrary.SHOW_EXCEPTION_STACKS && exception instanceof IgnoredException)
			return "";
		if (!BatchFitLibrary.SHOW_EXCEPTION_STACKS && exception instanceof FitLibraryExceptionInHtml)
			return "<hr/>" + Fixture.label(exception.getMessage());
		if (!BatchFitLibrary.SHOW_EXCEPTION_STACKS && exception instanceof FitLibraryException)
			return "<hr/>" + Fixture.label(HtmlUtils.escapeHtml(exception.getMessage()));
		final StringWriter buf = new StringWriter();
		exception.printStackTrace(new PrintWriter(buf));
		return "<hr><pre><div class=\"fit_stacktrace\">" + (buf.toString()) + "</div></pre>";
	}

	// @Override
	public Throwable unwrapThrowable(Throwable throwable) {
		return ExceptionHandler.unwrap(throwable);
	}

	// @Override
	public boolean unwrappedIsShow(Exception e) {
		return unwrapThrowable(e) instanceof FitLibraryShowException;
	}
}
