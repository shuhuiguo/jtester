/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.definedAction;

import java.io.File;
import java.util.concurrent.BlockingQueue;

import fitlibrary.DefineAction;
import fitlibrary.batch.fitnesseIn.ParallelFitNesseRepository;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.definedAction.DefinedActionBodyCollector.DefineActionBodyConsumer;
import fitlibrary.runResults.TestResultsFactory;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.ParseUtility;

public class DefineActionsOnPageSlowly {
	protected String topPageName;
	private static String FITNESSE_DIRY = ".";
	protected final RuntimeContextInternal runtime;

	public static void setFitNesseDiry(String diry) {
		FITNESSE_DIRY = diry;
	}

	public DefineActionsOnPageSlowly(String topPageName, RuntimeContextInternal runtime) {
		this.topPageName = topPageName;
		this.runtime = runtime;
	}

	public void process() throws Exception {
		processPages(topPageName.substring(1));
	}

	private void processPages(String pageName) throws Exception {
		ParallelFitNesseRepository parallelFitNesseRepository = new ParallelFitNesseRepository(FITNESSE_DIRY);
		BlockingQueue<TestDescriptor> queue = parallelFitNesseRepository.getDefinedActions(pageName);
		while (true) {
			TestDescriptor test = queue.take();
			if (ParallelFitNesseRepository.isSentinel(test))
				break;
			String html = ParseUtility.tabulize(test.getContent());
			if (html.contains("<table"))
				parseDefinitions(TableFactory.tables(html), determineClassName(pageName, test.getName()),
						test.getName());
		}
	}

	protected String determineClassName(String prefix, String pageName) {
		String fullPageName = prefix + "." + pageName;
		if ("".equals(pageName))
			fullPageName = prefix;
		int classPos = fullPageName.lastIndexOf(".Class");
		if (classPos >= 0) {
			int nextDotPos = fullPageName.indexOf(".", classPos + 1);
			if (nextDotPos >= 0)
				return fullPageName.substring(classPos + 6, nextDotPos);
		}
		return "";
	}

	protected void parseDefinitions(Tables tables, final String className, final String pageName) {
		new DefinedActionBodyCollector().parseDefinitions(tables, new DefineActionBodyConsumer() {
			// @Override
			public void addAction(Tables innerTables) {
				defineAction(innerTables, className, pageName);
			}
		});
	}

	protected void defineAction(Tables innerTables, String className, String pageName) {
		DefineAction defineAction = new DefineAction(className, pageName);
		defineAction.setRuntimeContext(runtime);
		defineAction.define(createDefineActionTable(innerTables), TestResultsFactory.testResults());
	}

	private Table createDefineActionTable(Tables innerTables) {
		Table defineActionTable = TableFactory.table();
		Row row = TableFactory.row();
		row.addCell("DefineAction");
		defineActionTable.add(row);
		row = TableFactory.row();
		row.add(TableFactory.cell(innerTables));
		defineActionTable.add(row);
		return defineActionTable;
	}

	protected File fitNesseDiry() {
		return new File(FITNESSE_DIRY);
	}
}
