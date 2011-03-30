/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import fit.Parse;
import fitlibrary.differences.FolderRunnerDifference;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.traverse.Traverse;
import fitlibrary.utility.ParseUtility;

/**
 * Runs all the spreadsheet and HTML files in the given directory, and
 * sub-directories. Generates reports in the given directory, and
 * sub-directories.
 */
public class FolderRunner extends FileParseUtilities {
	public static final String FILES = "files";
	public static final String INDEX_HTML = "reportIndex.html";
	protected String encoding = ParseUtility.ASCII_ENCODING;
	private Report topReport;
	private List<StoryTestListener> testListeners = new ArrayList<StoryTestListener>();
	private File inDiry;
	private File reportDiry;
	private File suiteFile;
	private BatchFitLibrary batchFitLibrary = new BatchFitLibrary();
	private RunFile runFile;

	public static void main(String[] args) throws ParseException, IOException {
		Report report = new FolderRunner(args).run();
		report.exit();
	}

	public FolderRunner() {
		Traverse.setDifferenceStrategy(new FolderRunnerDifference());
	}

	public FolderRunner(String[] args) {
		this();
		int prefixArgs = 0;
		if (args.length > 1 && args[0].equals("-s")) {
			prefixArgs = 2;
			setSuiteFile(args[1]);
		}
		switch (args.length - prefixArgs) {
		case 0:
			setTestFile("tests");
			break;
		case 1:
			setTestFile(args[prefixArgs]);
			break;
		case 2:
			setFiles(args[prefixArgs], args[prefixArgs + 1]);
			break;
		case 3:
			setFiles(args[prefixArgs], args[prefixArgs + 1]);
			this.encoding = args[prefixArgs + 2];
			break;
		default:
			System.err
					.println("Usage: java fitlibrary.runner.FolderRunner\n"
							+ "Or:    java fitlibrary.runner.FolderRunner testFolder\n"
							+ "Or:    java fitlibrary.runner.FolderRunner testFolder reportFolder\n"
							+ "Or:    java fitlibrary.runner.FolderRunner testFolder reportFolder unicodeEncoding"
							+ "Or:    java fitlibrary.runner.FolderRunner -s suiteFileName testFolder\n"
							+ "Or:    java fitlibrary.runner.FolderRunner -s suiteFileName testFolder reportFolder\n"
							+ "Or:    java fitlibrary.runner.FolderRunner -s suiteFileName testFolder reportFolder unicodeEncoding");
			System.exit(-1);
		}
	}

	private void setSuiteFile(String fileName) {
		this.suiteFile = new File(fileName);
	}

	public Report run(String testDiry) throws ParseException, IOException {
		runFile = new RunFile(encoding, testListeners, batchFitLibrary);
		setTestFile(testDiry);
		return run();
	}

	public Report run(String theInDiry, String theReportDiry) throws ParseException, IOException {
		setFiles(theInDiry, theReportDiry);
		return run();
	}

	public Report run() throws ParseException, IOException {
		runFile = new RunFile(encoding, testListeners, batchFitLibrary);
		if (!inDiry.exists() || !inDiry.isDirectory())
			throw new RuntimeException("Folder is needed for input: " + inDiry.getAbsolutePath());
		if (reportDiry.exists()) {
			if (!reportDiry.isDirectory())
				throw new RuntimeException("File exists but is not a directory: " + reportDiry.getAbsolutePath());
		} else if (!reportDiry.mkdir())
			throw new RuntimeException("Unable to create folder " + reportDiry.getAbsolutePath());
		if (reportDiry.getAbsolutePath().startsWith(inDiry.getAbsolutePath()))
			throw new RuntimeException("The reports folder can't be inside the tests folder: it'll run forever!");
		CopyFilesFromJar.copyCssAndImageFilesFromJar(inDiry);
		Parse setUpTables = new Parse("table", "", null, null);
		Parse tearDownTables = null;
		String title = "";
		File topReportDiry = reportDiry;
		topReport = new Report("FolderRunner", reportDiry, "", topReportDiry);
		File reportFile = new File(reportDiry, INDEX_HTML); // exposed critical
															// region between
															// this and next
		if (fileIsLocked(reportFile))
			throw new RuntimeException("Already running");
		if (suiteFile != null)
			runSuite(suiteFile, reportDiry, topReport, setUpTables, tearDownTables);
		runDiry(title, inDiry, reportDiry, topReport, setUpTables, tearDownTables, "", topReportDiry);
		giveFeedbackToUser();
		suiteFinished();
		topReport.setFinished();
		writeReport(reportFile, topReport);
		return topReport;
	}

	private void runDiry(String title, File theInDiry, File theReportDiry, Report parentReport, Parse setUpTables,
			Parse tearDownTables, String path, File topReportDiry) throws ParseException, IOException {
		FolderRunnerDifference.setCurrentTestDiryFile(theInDiry);
		Report report = new Report(title, theReportDiry, parentReport, path, topReportDiry);
		CollectSetUpTearDown collectSetUpTearDown = new CollectSetUpTearDown(encoding);
		Parse fullSetUpTables = collectSetUpTearDown.appendSetUp(setUpTables, theInDiry);
		Parse fullTearDownTables = collectSetUpTearDown.prependTearDown(tearDownTables, theInDiry);

		File filesFile = new File(theInDiry, FILES);
		if (filesFile.exists())
			CopyFiles.copyFilesRecursively(theInDiry, theReportDiry, FILES);

		File[] files = theInDiry.listFiles();
		for (int i = 0; i < files.length; i++) {
			giveFeedbackToUser();
			File file = files[i];
			String name = file.getName();
			if (file.isDirectory()) {
				if (canRunThisFolder(name)) {
					File subReportDiry = new File(theReportDiry, name);
					if (!subReportDiry.exists())
						subReportDiry.mkdir();
					runDiry(title + "." + file.getName(), file, subReportDiry, report, fullSetUpTables,
							fullTearDownTables, path + "../", topReportDiry);
					FolderRunnerDifference.setCurrentTestDiryFile(theInDiry);
				}
			} else if (!CollectSetUpTearDown.specialFileName(name))
				runFile(file, theReportDiry, report, fullSetUpTables, fullTearDownTables);
		}
		report.setFinished();
	}

	private void writeReport(File reportFile, Report report) throws IOException {
		Report reportToWrite = report;
		if (report.hasSingleChild())
			reportToWrite = report.firstChild();
		PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(reportFile)));
		output.print(reportToWrite.getHtml());
		output.close();
	}

	private void runSuite(File theSuiteFile, File theReportDiry, Report parentReport, Parse setUpTables,
			Parse tearDownTables) {
		if (!theSuiteFile.exists() || theSuiteFile.isDirectory())
			throw new RuntimeException("Suite file doesn't exist or is not a file: " + theSuiteFile);
		Report report = new Report(theSuiteFile.getName(), theReportDiry, parentReport, "", theReportDiry);
		runFile(theSuiteFile, theReportDiry, report, setUpTables, tearDownTables);
		report.setFinished();
	}

	private boolean canRunThisFolder(String name) {
		return !name.equals(FILES) && !name.startsWith(".") && !name.equals("CVS");
	}

	private void runFile(File file, File theReportDiry, Report report, Parse fullSetUpTables, Parse fullTearDownTables) {
		runFile.runFile(file, theReportDiry, report, fullSetUpTables, fullTearDownTables);
	}

	private void setTestFile(String testDiryName) {
		inDiry = new File(testDiryName);
		reportDiry = new File(inDiry.getParentFile(), "reports");
	}

	private void setFiles(String testDiryName, String reportDiryName) {
		inDiry = new File(testDiryName);
		reportDiry = new File(reportDiryName);
	}

	public void addTestListener(StoryTestListener listener) {
		testListeners.add(listener);
	}

	private void giveFeedbackToUser() {
		for (StoryTestListener listener : testListeners)
			listener.testComplete(topReport.failing(), topReport.getCounts(), topReport.getAssertionCounts());
	}

	private void suiteFinished() {
		for (StoryTestListener listener : testListeners)
			listener.suiteComplete();
	}

	public void exit() {
		topReport.exit();
	}
}
