/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.global.PlugBoard;
import fitlibrary.global.TemporaryPlugBoardForRuntime;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.traverse.workflow.caller.ValidCall;

// This us not being used now.
// But keep it in case we reintroduce plain text for fixture methods too.
public class PlainText {
	private final Row row;
	private final TestResults testResults;
	private final DoTraverse doTraverse;
	private String prefixAction = "";
	private String infixAction = "";
	private String infixPart = "";

	public PlainText(Row row, TestResults testResults, DoTraverse doTraverse) {
		this.row = row;
		this.testResults = testResults;
		this.doTraverse = doTraverse;
	}

	public void analyse() {
		String textCall = row.at(1).fullText();
		List<ValidCall> results = new ArrayList<ValidCall>();

		findDefinedActionCallsFromPlainText(textCall, results);

		textCall = infixes(prefixes(textCall));
		findProperty("get", textCall, results);
		findProperty("is", textCall, results);
		findMethodsFromPlainText(textCall, results);

		row.removeElementAt(0);
		if (results.isEmpty()) {
			error("Unknown action");
			return;
		}
		if (results.size() > 1) {
			error("Ambiguous action (see details in logs after table)");
			doTraverse.showAfterTable("Possible action tables:<br/>");
			for (ValidCall call : results)
				call.possibility(doTraverse.getRuntimeContext().getGlobal());
			return;
		}
		row.clear();
		if (!"".equals(prefixAction)) {
			row.addCell("<b>" + prefixAction + "</b>");
		}
		for (String word : results.get(0).getList())
			row.addCell(word);
		if (!"".equals(infixPart)) {
			row.addCell("<b>" + infixAction + "</b>");
			row.addCell(infixPart);
		}
		doTraverse.interpretRowBeforeWrapping(row, testResults);
	}

	private void findDefinedActionCallsFromPlainText(String textCall, List<ValidCall> results) {
		TemporaryPlugBoardForRuntime.definedActionsRepository().findPlainTextCall(textCall, results);

	}

	private void findMethodsFromPlainText(String textCall, List<ValidCall> results) {
		PlugBoard.lookupTarget.findMethodsFromPlainText(textCall, results, doTraverse.getRuntimeContext());
	}

	private void error(String message) {
		row.at(0).error(testResults, message);
	}

	private void findProperty(String prefix, String textCall, List<ValidCall> results) {
		int count = results.size();
		findMethodsFromPlainText(doTraverse.extendedCamel(prefix + " " + textCall), results);
		if (results.size() > count)
			results.get(results.size() - 1).setCall(textCall);
	}

	private String prefixes(String textCallOriginal) {
		String textCall = textCallOriginal;
		String[] prefixes = { "not", "reject", "show", "show after", "ensure" };
		for (String prefix : prefixes) {
			String prefixString = prefix;
			if (textCall.startsWith(prefixString)) {
				textCall = textCall.substring(prefixString.length());
				prefixAction = prefix;
			}
		}
		return textCall;
	}

	private String infixes(String textCallOriginal) {
		String textCall = textCallOriginal;
		String[] infixes = { "is", "is not", "matches", "eventually matches", "does not match", "becomes", "contains",
				"eventually contains" };
		for (String infix : infixes) {
			String infixString = "*" + infix + "*";
			int pos = textCall.indexOf(infixString);
			if (pos >= 0) {
				infixPart = textCall.substring(pos + infixString.length());
				textCall = textCall.substring(0, pos);
				infixAction = infix;
			}
		}
		return textCall;
	}
}
