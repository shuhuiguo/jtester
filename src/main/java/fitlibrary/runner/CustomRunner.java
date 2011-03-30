/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.*;
import java.util.Date;

import fit.Counts; // With Fit itself, this class is Fixture.Counts.
import fit.Fixture;
import fit.Parse;

/**
 * Help with building custom runners for Fit. For a simple example, see
 * ExampleOfCustomRunner.java
 */
public class CustomRunner extends MakeParse {
	protected Fixture fixture = new Fixture();
	private File reportFile = null;

	public CustomRunner(String webPageTitle, File inFile, String reportFileName) {
		this(webPageTitle, inFile, new File(reportFileName));
	}

	public CustomRunner(String webPageTitle, File inFile, File reportFile) {
		super(webPageTitle);
		this.reportFile = reportFile;
		if (inFile != null) {
			fixture.summary.put("input file", inFile.getAbsolutePath());
			fixture.summary.put("input update", new Date(inFile.lastModified()));
		}
		if (reportFile != null)
			fixture.summary.put("output file", reportFile.getAbsolutePath());
	}

	public CustomRunner(String webPageTitle, String reportFileName) {
		this(webPageTitle, null, reportFileName);
	}

	public CustomRunner(String webPageTitle) {
		this(webPageTitle, "report.html");
	}

	public CustomRunner() {
		this("CustomRunner");
	}

	public CustomRunner(String webPageTitle, CustomRunner previousRunner) {
		this(webPageTitle);
		fixture.counts = previousRunner.fixture.counts;
	}

	public void runAndReportAndExit() throws IOException, CustomRunnerException {
		System.exit(runAndReportCounts());
	}

	public int runAndReportCounts() throws IOException, CustomRunnerException {
		runAndReport();
		System.err.println(fixture.counts());
		return errorCount();
	}

	public Counts runAndReport() throws IOException, CustomRunnerException {
		run();
		print();
		return fixture.counts;
	}

	public void print() throws IOException {
		PrintWriter report = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)));
		try {
			print(report);
		} finally {
			report.close();
		}
	}

	public void run() throws RuntimeException {
		Parse tables = getTables();
		if (tables != null)
			fixture.doTables(tables);
	}

	public int errorCount() {
		return fixture.counts.wrong + fixture.counts.exceptions;
	}
}
