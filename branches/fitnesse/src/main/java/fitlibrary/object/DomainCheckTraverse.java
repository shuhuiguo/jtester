/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.object;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.NoSystemUnderTestException;
import fitlibrary.exception.method.MissingMethodException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.ITableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;

public class DomainCheckTraverse extends Traverse implements TableEvaluator {
	private DomainTraverser domainTraverser;

	public DomainCheckTraverse() {
		//
	}

	public DomainCheckTraverse(Object sut) {
		super(sut);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		table.error(testResults, new RuntimeException("Don't expect to have this called!"));
		return null;
	}

	public void setDomainTraverse(DomainTraverser domainTraverser) {
		this.domainTraverser = domainTraverser;
	}

	// @Override
	public void runTable(Table table, ITableListener tableListener) {
		if (switchOnExpected(table)) {
			domainTraverser.setCurrentAction();
			return;
		}
		try {
			for (int rowNo = 0; rowNo < table.size(); rowNo++) {
				Row row = table.at(rowNo);
				if (row.text(0, this).equals("comment"))
					return;
				processRow(row, tableListener.getTestResults());
			}
		} catch (Exception e) {
			table.error(tableListener, e);
		}
	}

	private void processRow(Row row, TestResults testResults) {
		for (int i = 0; i < row.size(); i += 2) {
			Cell cell = row.at(i);
			Cell cell2 = row.at(i + 1);
			if (DomainObjectSetUpTraverse.givesClass(cell, this)) {
				if (getSystemUnderTest() == null)
					throw new NoSystemUnderTestException();
				checkClass(testResults, cell, cell2);
				// ClassMethodTarget target = new
				// ClassMethodTarget(getSystemUnderTest());
				// target.invokeAndCheckCell(cell2,true,testResults);
			} else {
				try {
					ICalledMethodTarget target = PlugBoard.lookupTarget.findGetterOnSut(cell.text(this), this, "Type");
					target.invokeAndCheck(TableFactory.row(), cell2, testResults, false);
				} catch (MissingMethodException ex) {
					cell.error(testResults, ex);
				}
			}
		}
	}

	private void checkClass(TestResults testResults, Cell cell, Cell classCell) {
		String typeName = classCell.text(this);
		try {
			Class<?> sutClass = PlugBoard.lookupTarget.findClassFromFactoryMethod(this, getTypedSystemUnderTest()
					.classType(), typeName);
			if (getSystemUnderTest().getClass().equals(sutClass))
				classCell.pass(testResults);
			else
				classCell.fail(testResults);
		} catch (Exception e) {
			cell.error(testResults, e);
		}
	}

	private boolean switchOnExpected(Table table) {
		return domainTraverser != null && table.size() == 1 && table.at(0).size() == 1
				&& table.at(0).at(0).matchesTextInLowerCase("expected", this);
	}

	// @Override
	public void addNamedObject(String text, TypedObject typedObject, Row row, TestResults testResults) {
		// TODO Auto-generated method stub
		// Remove this later
	}

	// @Override
	public void select(String name) {
		// TODO Auto-generated method stub
		// Remove this later
	}

	// @Override
	public void runInnerTables(Tables definedActionBody, ITableListener tableListener) {
		// TODO Auto-generated method stub
		// Remove this later
	}
}
