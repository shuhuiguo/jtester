/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import fit.Counts;
import fit.Parse;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.traverse.Traverse;

public class RunFile extends FileParseUtilities {
	private BatchFitLibrary batchFitLibrary;
	private String encoding;
	private List<StoryTestListener> testListeners;

	public RunFile(String encoding, List<StoryTestListener> testListeners, BatchFitLibrary batchFitLibrary) {
		this.encoding = encoding;
		this.testListeners = testListeners;
		this.batchFitLibrary = batchFitLibrary;
	}

	public void runFile(File file, File theReportDiry, Report report, Parse fullSetUpTables, Parse fullTearDownTables) {
		String name = file.getName();
		PrintStream oldOut = System.out;
		PrintStream oldErr = System.err;
		ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
		ByteArrayOutputStream tempErr = new ByteArrayOutputStream();
		try {
			if (isXlsFileName(name) || isHtmlFileName(name)) {
				System.setOut(new PrintStream(tempOut));
				System.setErr(new PrintStream(tempErr));
				File reportFile = new File(theReportDiry, reportName(file));
				if (fileIsLocked(reportFile))
					throw new RuntimeException("File is locked");
				Parse setUp = copyParse(fullSetUpTables.more);
				Parse tearDown = copyParse(fullTearDownTables);
				Traverse.setContext(theReportDiry);
				Counts counts;
				if (isXlsFileName(name))
					counts = new SpreadsheetRunner(report).run(file, reportFile, setUp, tearDown, batchFitLibrary);
				else
					counts = new HtmlRunner(report).runInSuite(file, reportFile, encoding, setUp, tearDown,
							batchFitLibrary);
				report.addAssertionCountsForPage(reportFile, counts);
			} else
				throw new RuntimeException("Not HTML nor XLS");
		} catch (Exception e) {
			ignoreFile(file, e);
		} finally {
			reportOutput(name, "out", tempOut.toString());
			reportOutput(name, "err", tempErr.toString());
			System.setOut(oldOut);
			System.setErr(oldErr);
		}
	}

	private void reportOutput(String name, String out, String message) {
		for (StoryTestListener listener : testListeners)
			listener.reportOutput(name, out, message);
	}
}
