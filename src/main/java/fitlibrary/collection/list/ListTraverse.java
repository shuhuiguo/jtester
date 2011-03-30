/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.list;

import java.util.List;

import fitlibrary.closure.MethodTarget;
import fitlibrary.collection.CollectionTraverse;
import fitlibrary.exception.table.ExtraCellsException;
import fitlibrary.exception.table.MissingCellsException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;

/**
 * Like fit.RowFixture, except that the order of rows is important and
 * properties are supported. The algorithm used for matching is very simple; a
 * diff approach may be better.
 * 
 * o Instead of running over the actual elements, run over the bindings for
 * those objects o Delete the bindings as they match o No need to set the
 * targets because that's already done o Instead of building surplus from the
 * remaining elements, do it from remaining bindings. o Handle the case where an
 * element doesn't have a field, in which case the expected should be empty o
 * Take a Collection as an arg to the constructor
 * 
 * See the FitLibrary specifications for examples
 */
public class ListTraverse extends CollectionTraverse {
	public ListTraverse(Object sut) {
		super(sut);
	}

	public ListTraverse(Object sut, Object actuals) {
		super(sut, actuals);
	}

	public ListTraverse() {
		super(null);
	}

	@Override
	public void interpretRow(Row row, List<MethodTarget[]> remainingActuals, TestResults testResults) throws Exception {
		if (remainingActuals.isEmpty()) {
			row.missing(testResults);
			return;
		}
		int rowLength = row.size();
		MethodTarget[] columnBindings = remainingActuals.get(0);
		if (rowLength < columnBindings.length)
			throw new MissingCellsException("ArrayTraverse");
		if (rowLength > columnBindings.length)
			throw new ExtraCellsException("ArrayTraverse");
		if (!remainingActuals.isEmpty() && matchRow(row, columnBindings, testResults))
			remainingActuals.remove(0);
		else
			row.missing(testResults);
	}
}
