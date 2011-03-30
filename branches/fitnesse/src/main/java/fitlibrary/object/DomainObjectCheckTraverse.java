/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.object;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.NoSystemUnderTestException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

public class DomainObjectCheckTraverse extends Traverse {
	private Class<?> type;

	public DomainObjectCheckTraverse(Object sut) {
		super(sut);
		if (sut != null)
			this.type = sut.getClass();
	}

	public DomainObjectCheckTraverse(TypedObject typedObject) {
		super(typedObject);
		this.type = typedObject.classType();
	}

	public DomainObjectCheckTraverse(Object sut, Typed typed) {
		super(typed.typedObject(sut));
		this.type = typed.asClass();
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		for (int rowNo = 1; rowNo < table.size(); rowNo++)
			interpret(table.at(rowNo), testResults);
		return getSystemUnderTest();
	}

	public void interpret(Row row, TestResults testResults) {
		for (int i = 0; i < row.size(); i += 2) {
			Cell cell = row.at(i);
			if (DomainObjectSetUpTraverse.givesClass(cell, this)) {
				if (getSystemUnderTest() == null)
					throw new NoSystemUnderTestException();
				checkClass(testResults, cell, row.at(i + 1));
			} else {
				try {
					ICalledMethodTarget target = PlugBoard.lookupTarget.findGetterOnSut(cell.text(this), this, "Type");
					target.invokeAndCheck(TableFactory.row(), row.at(i + 1), testResults, false);
				} catch (Exception e) {
					cell.error(testResults, e);
				}
			}
		}
	}

	private void checkClass(TestResults testResults, Cell cell, Cell classCell) {
		String typeName = classCell.text(this);
		try {
			Class<?> sutClass = PlugBoard.lookupTarget.findClassFromFactoryMethod(this, type, typeName);
			if (getSystemUnderTest().getClass().equals(sutClass))
				classCell.pass(testResults);
			else
				classCell.fail(testResults);
		} catch (Exception e) {
			cell.error(testResults, e);
		}
	}
}
