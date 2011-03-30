/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.suite;

import java.util.Date;

import org.apache.log4j.Logger;

import fit.FitServerBridge;
import fit.exception.FitParseException;
import fitlibrary.log.ConfigureLoggingThroughFiles;
import fitlibrary.log.FitLibraryLogger;
import fitlibrary.runResults.TableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.runResults.TestResultsOnCounts;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class FitLibraryServer extends FitServerBridge {
	static Logger logger = FitLibraryLogger.getLogger(FitLibraryServer.class);
	private BatchFitLibrary batching = new BatchFitLibrary();

	@Override
	public TestResults doTables(String html) {
		try {
			return doTables(TableFactory.tables(html));
		} catch (FitParseException e) {
			e.printStackTrace();
		}
		return new TestResultsOnCounts();
	}

	public TestResults doTables(Tables theTables) {
		TableListener tableListener = new TableListener(reportListener);
		batching.doTables(theTables, tableListener);
		return tableListener.getTestResults();
	}

	@Override
	public void exit() throws Exception {
		batching.exit();
		super.exit();
	}

	@Override
	protected void usage() {
		logger.trace("usage: java fitlibrary.suite.FitLibraryServer [-v] host port socketTicket");
		System.exit(-1);
	}

	public static void main(String[] args) {
		ConfigureLoggingThroughFiles.configure();
		FitServerBridge fitServer = new FitLibraryServer();
		try {
			logger.trace(new Date());
			fitServer.run(args);
			logger.trace(("Exit: " + fitServer.exitCode()));
			if (fitServer.isExit())
				System.exit(fitServer.exitCode());
		} catch (Exception e) {
			fitServer.printExceptionDetails(e);
		}
	}
}