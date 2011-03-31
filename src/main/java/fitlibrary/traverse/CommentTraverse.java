/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse;

import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;

public class CommentTraverse extends Traverse {
	private boolean markAsIgnored;

	public CommentTraverse() {
		this(false);
	}

	public CommentTraverse(boolean markAsIgnored) {
		setMarkAsIgnored(markAsIgnored);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		if (markAsIgnored)
			table.ignore(testResults);
		return null;
	}

	public void setMarkAsIgnored(boolean markAsIgnored) {
		this.markAsIgnored = markAsIgnored;
	}
}
