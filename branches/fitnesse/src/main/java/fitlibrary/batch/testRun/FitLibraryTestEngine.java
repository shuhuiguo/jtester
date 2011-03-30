/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.batch.testRun;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import fit.Counts;
import fit.Fixture;
import fit.exception.FitParseException;
import fitlibrary.batch.trinidad.SingleTestResult;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestEngine;
import fitlibrary.batch.trinidad.TestResult;
import fitlibrary.runResults.TableListener;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class FitLibraryTestEngine implements TestEngine {
	public static final String NOT_A_TEST = "NOT-A-TEST";
	private FitLibraryBatching batching;

	public FitLibraryTestEngine() {
		this(new FitLibraryBatchingImp());
	}

	public FitLibraryTestEngine(FitLibraryBatching batching) {
		this.batching = batching;
	}

	// @Override
	public TestResult runTest(TestDescriptor test) {
		PrintStream out = System.out;
		OutputStream tempOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(tempOut));
		PrintStream err = System.err;
		OutputStream tempErr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(tempErr));
		try {
			return runTest(test, tempOut, tempErr);
		} finally {
			System.setOut(out);
			System.setErr(err);
		}
	}

	private TestResult runTest(TestDescriptor test, OutputStream out, OutputStream err) {
		String content = test.getContent();
		if (content.equals(NOT_A_TEST))
			return new SingleTestResult(new Counts(), test.getName(), " not a Test", 0);
		if (!content.contains("<table"))
			return new SingleTestResult(new Counts(), test.getName(), " contains no tables", 0);
		try {
			long time = System.currentTimeMillis();
			Tables tables = TableFactory.tables(content);
			TableListener listener = new TableListener();
			batching.setCurrentPageName(test.getName());
			batching.doTables(tables, listener);
			String report = tables.report();
			report = add("out", out, report);
			report = add("err", err, report);
			return new SingleTestResult(listener.getTestResults().getCounts(), test.getName(), report,
					System.currentTimeMillis() - time);
		} catch (FitParseException e) {
			Counts counts = new Counts();
			counts.exceptions = 1;
			return new SingleTestResult(counts, test.getName(), e.toString(), 0);
		}
	}

	private String add(String header, OutputStream out, String report) {
		String s = out.toString();
		if (s.equals(""))
			return report;
		int body = report.indexOf("<div class=\"footer\">");
		if (body < 0)
			body = report.indexOf("</body");
		if (body < 0)
			body = report.length();
		return report.substring(0, body) + "\n<hr/><h1>" + header + "</h1>\n<pre>\n" + Fixture.escape(s) + "\n</pre>\n"
				+ report.substring(body);
	}

	static class FitLibraryBatchingImp implements FitLibraryBatching {
		BatchFitLibrary batching = new BatchFitLibrary();

		// @Override
		public void doTables(Tables tables, TableListener listener) {
			batching.doTables(tables, listener);
		}

		// @Override
		public void setCurrentPageName(String name) {
			batching.setCurrentPageName(name);
		}
	}
}
