/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.debug;

import fit.FitServerBridge;
import fit.exception.FitParseException;
import fitlibrary.runResults.TableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.suite.BatchFitLibrary;
import fitlibrary.suite.ReportListener;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class RunDirectly {
	protected ReportListener reportListener = new ReportListener() {
		// @Override
		public void tableFinished(Table table) {
			//
		}

		// @Override
		public void tablesFinished(TestResults testResults) {
			//
		}
	};
	BatchFitLibrary batchFitLibrary = new BatchFitLibrary(new TableListener(reportListener));

	private void run(String wiki) throws FitParseException {
		String html = html(wiki);
		System.out.println("\n----------\nHTML\n----------\n" + html);
		Tables tables = TableFactory.tables(html);
		FitServerBridge.setFitNesseUrl(""); // Yuck passing important info
											// through a global. See method for
											// links.
		TestResults testResults = batchFitLibrary.doStorytest(tables);
		System.out.println("\n----------\nHTML Report\n----------\n" + tables.report());
		System.out.println(testResults);
	}

	private String html(String wiki) {
		String result = "<table><tr><td>fitlibrary.specify.dynamicVariable.DynamicVariablesUnderTest</td></tr></table>";
		return result;
	}

	private static void running(String wiki) {
		try {
			new RunDirectly().run(wiki);
		} catch (FitParseException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String html = "|a|";
		running(html);
	}
}
