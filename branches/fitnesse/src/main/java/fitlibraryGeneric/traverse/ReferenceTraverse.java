/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibraryGeneric.traverse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fit.exception.FitFailureException;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.IgnoredException;
import fitlibrary.exception.method.MissingMethodException;
import fitlibrary.exception.table.MissingCellsException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.traverse.Traverse;

public class ReferenceTraverse extends Traverse {
	private static String[] WORD_COUNTS = { "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eigth",
			"nineth", "tenth" };
	private static Map<String, Integer> MAP_WORD_TO_INT = new HashMap<String, Integer>();

	static {
		int i = 0;
		for (String word : WORD_COUNTS) {
			MAP_WORD_TO_INT.put(word, i++);
		}
	}

	public ReferenceTraverse() {
		// No SUT
	}

	public ReferenceTraverse(Object sut) {
		super(sut);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults results) {
		table.error(results, new FitFailureException("Not expected"));
		return null;
	}

	public static boolean applicable(Table table) {
		if (table.size() == 1)
			return isUses(table.at(0).at(0).text());
		return false;
	}

	private static boolean isUses(String text) {
		return text.toLowerCase().equals("use");
	}

	public Object interpretReference(Table table, TestResults testResults) throws Exception {
		return getObject(table.at(0), 1, getSystemUnderTest(), testResults);
	}

	private Object getObject(Row row, int cellNo, Object initialObject, TestResults testResults) {
		Object object = initialObject;
		boolean last = false;
		if (row.atExists(cellNo + 1)) {
			Cell nextCell = row.at(cellNo + 1);
			if (!nextCell.matchesTextInLowerCase("of", this) && !nextCell.matchesTextInLowerCase("in", this)) {
				nextCell.fail(testResults, "'of' or 'in' expected", this);
				throw new IgnoredException();
			}
			if (!row.atExists(cellNo + 2)) {
				nextCell.error(testResults, new MissingCellsException(""));
				throw new IgnoredException();
			}
			object = getObject(row, cellNo + 2, object, testResults);
		} else
			last = true;
		return getReferencedObject(row.at(cellNo), object, last, testResults);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object getReferencedObject(Cell cell, Object object, boolean last, TestResults testResults) {
		if (object instanceof List)
			return getElementOfList(cell, (List<Object>) object);
		if (object.getClass().isArray())
			return getElementOfArray(cell, (Object[]) object);
		if (object instanceof Map)
			return getElementOfMap(cell, (Map) object, testResults);
		return getPropertyOfObject(cell, object, last, testResults);
	}

	private Object getPropertyOfObject(Cell cell, Object object, boolean last, TestResults testResults) {
		try {
			String name = cell.text(this);
			ICalledMethodTarget target = getGetterTarget(cell, name, object, last, testResults);
			return target.invoke(new Object[] {});
		} catch (IgnoredException e) {
			throw e;
		} catch (Exception e) {
			cell.error(testResults, e);
			throw new IgnoredException();
		}
	}

	private Object getElementOfMap(Cell cell, Map<String, Object> map, TestResults testResults) {
		Object object = map.get(cell.text(this));
		if (object == null) {
			cell.error(testResults, new FitFailureException("Reference failed"));
			throw new IgnoredException();
		}
		return object;
	}

	private Object getElementOfArray(Cell cell, Object[] objects) {
		Integer index = MAP_WORD_TO_INT.get(cell.textLower(this));
		if (index == null || index < 0 || index >= objects.length)
			throw new FitFailureException("Reference not defined: '" + cell.textLower(this) + "'");
		return objects[index];
	}

	private Object getElementOfList(Cell cell, List<Object> list) {
		Integer index = MAP_WORD_TO_INT.get(cell.textLower(this));
		if (index == null || index < 0 || index >= list.size())
			throw new FitFailureException("Reference not defined: '" + cell.textLower(this) + "'");
		return list.get(index);
	}

	private ICalledMethodTarget getGetterTarget(Cell cell, String name, Object sut, boolean considerContext,
			TestResults testResults) {
		try {
			return PlugBoard.lookupTarget.findGetterUpContextsToo(asTypedObject(sut), this, name, considerContext);
		} catch (MissingMethodException ex) {
			cell.error(testResults, ex);
			throw new IgnoredException();
		}
	}
}
