/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runtime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fitlibrary.table.Table;

public class FoldingTexts {
	private static int NEXT_ID = 12345;
	private static int nextId = NEXT_ID++;
	private Map<String, StringBuilder> folds = new ConcurrentHashMap<String, StringBuilder>();

	public void logAsAfterTable(String title, String message) {
		String br = "<br/>";
		if (title.equals("Logging"))
			br = "";
		StringBuilder sb = folds.get(title);
		if (sb == null)
			folds.put(title, new StringBuilder(message + br));
		else
			synchronized (sb) {
				sb.append(message);
				sb.append(br);
			}
	}

	public void addAccumulatedFoldingText(Table table) {
		for (String key : folds.keySet())
			addAccumulatedFoldingText(key, table);
	}

	private void addAccumulatedFoldingText(String title, Table table) {
		StringBuilder sb = folds.get(title);
		String text;
		synchronized (sb) {
			text = sb.toString();
			sb.setLength(0);
		}
		if (text == null || "".equals(text.trim()))
			return;
		final int id = nextId;
		nextId++;
		final String foldText = "<div class=\"included\">\n<div style=\"float: right;\" class=\"meta\">\n"
				+ "<a href=\"javascript:expandAll();\">Expand All</a> |\n <a href=\"javascript:collapseAll();\">Collapse All</a></div>\n"
				+ "<a href=\"javascript:toggleCollapsable('" + id + "');\">\n"
				+ "<img src=\"/files/images/collapsableClosed.gif\" class=\"left\" id=\"img" + id + "\"/></a>\n"
				+ "&nbsp;<span class=\"meta\">" + title + "</span><div class=\"hidden\" id=\"" + id + "\">\n"
				+ tabled(text, title) + "\n</div></div>\n";
		table.addFoldingText(foldText);
	}

	private String tabled(String text, String title) {
		if (title.equals("Logging"))
			return "<table border='1' cellspacing='0'>" + text + "</table>";
		return "<pre>" + text + "</pre>";
	}
}
