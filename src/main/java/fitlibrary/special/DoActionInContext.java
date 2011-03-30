/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.special;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.exception.IgnoredException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContext;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.tableProxy.CellProxy;

public class DoActionInContext implements DoAction {
	private final ICalledMethodTarget target;
	protected final Row row;
	private final int innerFrom;
	private final int from;
	private final int to;
	private final boolean sequencing;
	protected final RuntimeContextInternal runtime;

	public DoActionInContext(ICalledMethodTarget target, Row row, int innerFrom, int innerUpTo, boolean sequencing,
			RuntimeContextInternal runtime) {
		this.target = target;
		this.row = row;
		this.innerFrom = innerFrom;
		this.sequencing = sequencing;
		this.runtime = runtime;
		if (innerFrom == 0) {
			from = innerUpTo;
			to = row.size();
		} else {
			from = 0;
			to = innerFrom;
		}
	}

	// @Override
	public Object run() throws Exception {
		return run(true);
	}

	private Object run(boolean markErrors) throws Exception {
		Parser[] parameterParsers = target.getParameterParsers();
		Object[] args = new Object[parameterParsers.length];
		TestResults testResults = runtime.getTestResults();
		for (int i = 0; i < parameterParsers.length; i++) {
			try {
				args[i] = parameterParsers[i].parseTyped(offsetArgumentCell(i), testResults).getSubject();
			} catch (Exception e) {
				if (markErrors)
					offsetArgumentCell(i).error(testResults, e);
				throw new IgnoredException(e);
			}
		}
		return target.invoke(args);
	}

	// @Override
	public Object runWithNoColouring() throws Exception {
		return run(false);
	}

	private Cell offsetArgumentCell(int i) {
		if (sequencing)
			return row.at(innerFrom + i + 1);
		return row.at(innerFrom + i * 2 + 1);
	}

	// @Override
	public RuntimeContext getRuntime() {
		return runtime;
	}

	// @Override
	public CellProxy cellAt(final int i) {
		if (i < 0 || i >= to - from)
			throw new FitLibraryException("No special cell at " + i);
		final Cell cell = row.at(from + i);
		return new CellProxy() {
			// @Override
			public void pass() {
				cell.pass(runtime.getTestResults());
			}

			// @Override
			public void pass(String msg) {
				cell.pass(runtime.getTestResults(), msg);
			}

			// @Override
			public void fail() {
				cell.fail(runtime.getTestResults());
			}

			// @Override
			public void fail(String msg) {
				cell.fail(runtime.getTestResults(), msg, runtime.getResolver());
			}

			// @Override
			public void failHtml(String msg) {
				cell.failHtml(runtime.getTestResults(), msg);
			}

			// @Override
			public void error(String msg) {
				cell.error(runtime.getTestResults(), msg);
			}

			// @Override
			public void error(Throwable e) {
				cell.error(runtime.getTestResults(), e);
			}

			// @Override
			public void error() {
				cell.error(runtime.getTestResults());
			}
		};
	}

	// @Override
	public void showResult(Object result) throws Exception {
		runtime.show(target.getResultString(result));
	}

	@Override
	public String toString() {
		return target.toString();
	}

	// @Override
	public void show(String htmlString) {
		runtime.show(htmlString);
	}

	// @Override
	public void showAfter(Object result) throws Exception {
		runtime.showAsAfterTable("Logs", target.getResultString(result));
	}

	// @Override
	public void showAfterAs(String title, Object result) throws Exception {
		runtime.showAsAfterTable(title, target.getResultString(result));
	}
}
