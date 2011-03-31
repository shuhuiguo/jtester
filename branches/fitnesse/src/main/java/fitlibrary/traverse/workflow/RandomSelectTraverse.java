/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.workflow;

import java.util.Random;

import fitlibrary.exception.FitLibraryException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;

public class RandomSelectTraverse extends Traverse {
	private static Random random = new Random();
	private String var;

	public RandomSelectTraverse(String var) {
		this.var = var;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		if (table.size() <= 1)
			throw new FitLibraryException("Possible values are needed in subsequent rows of the table");
		int select = 1 + random.nextInt(table.size() - 1);
		setDynamicVariable(var, table.at(select).text(0, this));
		for (int i = 1; i < table.size(); i++)
			table.at(i).text(0, this);
		return null;
	}

}
