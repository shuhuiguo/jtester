/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.collection.array;

import java.lang.reflect.Array;

import fitlibrary.exception.table.RowWrongWidthException;
import fitlibrary.parser.Parser;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;
import fitlibraryGeneric.typed.GenericTypedObject;

/**
 * Handle checking of int[], Object[], etc
 */
public class ArrayTraverse extends Traverse {
	private final TypedObject typedArray;
	private Parser parser;
	private boolean embedded = false;

	public ArrayTraverse(Object array) {
		this.typedArray = new GenericTypedObject(array);
	}

	public ArrayTraverse(TypedObject typedArray) {
		this.typedArray = typedArray;
	}

	public ArrayTraverse(Object array, boolean embedded) {
		this(array);
		this.embedded = embedded;
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		this.parser = typedArray.getTyped().getComponentTyped().parser(this);
		int offset = 0;
		if (!embedded)
			offset = 1;
		int arrayLength = Array.getLength(typedArray.getSubject());
		int tableSize = table.size();
		if (tableSize == offset && arrayLength == 0 && offset == 1)
			table.at(0).at(0).pass(testResults);
		int rowNo;
		int arrayIndex = 0;
		for (rowNo = offset; rowNo < tableSize && arrayIndex < arrayLength; rowNo++) {
			Row row = table.at(rowNo);
			try {
				if (row.size() != 1)
					throw new RowWrongWidthException(1);
				if (parser.matches(row.at(0), get(arrayIndex), testResults)) {
					row.pass(testResults);
					arrayIndex++;
				} else
					row.at(0).expectedElementMissing(testResults);
			} catch (Exception e) {
				row.error(testResults, e);
			}
		}
		for (; rowNo < tableSize; rowNo++) {
			table.at(rowNo).missing(testResults);
		}
		for (; arrayIndex < arrayLength; arrayIndex++) {
			Row row = table.newRow();
			Cell cell = row.addCell();
			try {
				cell.actualElementMissing(testResults, parser.show(get(arrayIndex)));
			} catch (Exception e) {
				cell.error(testResults, e);
			}
		}
		return typedArray.getSubject();
	}

	private Object get(int rowNo) {
		return Array.get(typedArray.getSubject(), rowNo);
	}
}
