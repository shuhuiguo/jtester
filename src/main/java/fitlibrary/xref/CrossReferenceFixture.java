/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.xref;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

import fit.exception.FitParseException;
import fitlibrary.batch.fitnesseIn.ParallelFitNesseRepository;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.Traverse;

public class CrossReferenceFixture extends Traverse {
	private String suiteName;
	private Map<String, Set<String>> xref = new TreeMap<String, Set<String>>();
	private Map<String, Set<String>> definedActions = new TreeMap<String, Set<String>>();

	public CrossReferenceFixture(String suiteName) {
		this.suiteName = suiteName.substring(1);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		try {
			ParallelFitNesseRepository parallelFitNesseRepository = new ParallelFitNesseRepository(fitNesseDiry());
			BlockingQueue<TestDescriptor> queue = parallelFitNesseRepository.getSuite(suiteName);
			while (true) {
				TestDescriptor test = queue.take();
				if (ParallelFitNesseRepository.isSentinel(test))
					break;
				String html = test.getContent();
				if (html.contains("<table"))
					xref(test.getName(), TableFactory.tables(html));
			}
			table.add(TableFactory.row("<h1>Action calls</h1>", "(The ones that start with ~ may be due to data rows)"));
			addMapDataToTable(xref, table);
			if (!definedActions.isEmpty())
				table.add(TableFactory.row("<h1>Defined Actions</h1>", ""));
			addMapDataToTable(definedActions, table);
			table.at(0).at(0).pass(testResults);
		} catch (Exception e) {
			table.at(0).at(0).error(testResults, e);
		}
		return null;
	}

	protected String fitNesseDiry() {
		return ".";
	}

	private void xref(String pageName, Tables tables) throws FitParseException, InterruptedException, IOException {
		for (Table table : tables) {
			for (int rowNo = 0; rowNo < table.size(); rowNo++) {
				String action = actionOf(table.at(rowNo), getRuntimeContext());
				if (action != null)
					add(xref, action, pageName, rowNo == 0);
			}
		}
	}

	private void add(Map<String, Set<String>> map, String originalActionName, String pageName, boolean firstRow) {
		if (!firstRow && onlyNumbers(originalActionName))
			return;
		if (originalActionName.equals("") || originalActionName.equals("actions")
				|| originalActionName.equals("checks") || originalActionName.equals("get"))
			return;
		if (pageName.equals(""))
			return;
		String actionName = originalActionName;
		Set<String> set = map.get(actionName);
		if (set == null)
			if (firstRow) { // We may have already seen it in a second row
				set = map.get("~ " + actionName);
				if (set != null) { // yes, we have
					map.put(actionName, set);
					map.remove("~ " + actionName);
				}
			} else { // We haven't seen it in a first row
				actionName = "~ " + actionName;
				set = map.get(actionName);
			}
		if (set == null) {
			set = new TreeSet<String>();
			map.put(actionName, set);
		}
		set.add(pageName);
	}

	private boolean onlyNumbers(String name) {
		char[] charArray = name.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (!numberChar(charArray[i]))
				return false;
		}
		return true;
	}

	private boolean numberChar(char ch) {
		return Character.isDigit(ch) || ch == '.' || ch == ' ';
	}

	private String actionOf(Row row, RuntimeContextInternal runtime) throws FitParseException, InterruptedException,
			IOException {
		int start = 0;
		int pastEnd = row.size();
		String first = runtimeContext.extendedCamel(row.text(0, this));
		if (ignoreThisAction(pastEnd, first))
			return null;
		if (pastEnd == 2 && first.equals("defineActionsAt")) {
			processDefinedActions(row.text(1, this));
			return null;
		}
		if (postFixWithValue(postFixActionName(row, pastEnd, runtime)))
			pastEnd -= 2;
		else if (first.equals("check")) {
			start++;
			pastEnd--;
		} else if (prefixWithOneArg(first))
			start++;
		else if (first.equals("oo"))
			start += 2;
		else if (first.equals("set"))
			start += 2;
		else if (first.equals("show after"))
			start++;
		String result = "";
		for (int i = start; i < pastEnd; i += 2)
			result += " " + row.text(i, this);
		return result.trim();
	}

	private String postFixActionName(Row row, int pastEnd, RuntimeContextInternal runtime) {
		if (pastEnd > 2)
			return runtime.extendedCamel(row.text(pastEnd - 2, this));
		return "";
	}

	private boolean ignoreThisAction(int pastEnd, String first) {
		if (pastEnd == 1)
			return first.equals("comment") || first.equals("ignore") || first.equals("ignored")
					|| first.equals("ignoreTable");
		return first.equals("expectedTestResults") || first.equals("note");
	}

	private boolean postFixWithValue(String first) {
		return first.equals("is") || first.equals("is not") || first.equals("matches") || first.equals("doesNotMatch")
				|| first.equals("becomes");
	}

	private boolean prefixWithOneArg(String first) {
		return first.equals("not") || first.equals("reject") || first.equals("log") || first.equals("ensure")
				|| first.equals("show") || first.equals("showDot");
	}

	private void processDefinedActions(String definitionsName) throws InterruptedException, IOException,
			FitParseException {
		ParallelFitNesseRepository parallelFitNesseRepository = new ParallelFitNesseRepository(".");
		BlockingQueue<TestDescriptor> queue = parallelFitNesseRepository.getDefinedActions(definitionsName);
		while (true) {
			TestDescriptor test = queue.take();
			if (ParallelFitNesseRepository.isSentinel(test))
				break;
			String html = test.getContent();
			if (html.contains("<table")) {
				Tables tables = TableFactory.tables(html);
				boolean header = true;
				for (Table table : tables) {
					String pageName = definitionsName + "." + test.getName();
					if (pageName.endsWith("."))
						pageName = definitionsName;
					if (header) {
						add(definedActions, actionOf(table.at(0), getRuntimeContext()), pageName, true);
						header = false;
					} else {
						for (int rowNo = 0; rowNo < table.size(); rowNo++) {
							String action = actionOf(table.at(rowNo), getRuntimeContext());
							if (action != null)
								add(xref, action, pageName, rowNo == 0);
						}
						if (table.getTrailer() != null && table.getTrailer().contains("<hr/>"))
							header = true;
					}
				}
			}
		}
	}

	private void addMapDataToTable(Map<String, Set<String>> map, Table table) {
		for (String key : map.keySet()) {
			String list = "";
			for (String page : map.get(key))
				list += ", <a href=\"" + page + "\">" + page + "</a>";
			table.add(TableFactory.row(key, list.substring(2)));
		}
	}
}
