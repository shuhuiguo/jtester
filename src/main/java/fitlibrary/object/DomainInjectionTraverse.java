/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.object;

import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.IgnoredException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.ITableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;
import fitlibrary.traverse.TableEvaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.TypedObject;

public class DomainInjectionTraverse extends Traverse implements TableEvaluator {
	private DomainTraverser domainTraverser = null;

	public DomainInjectionTraverse() {
		//
	}

	public DomainInjectionTraverse(DomainTraverser domainTraverser) {
		this.domainTraverser = domainTraverser;
	}

	public void setDomainTraverse(DomainTraverser domainTraverser) {
		this.domainTraverser = domainTraverser;
	}

	// @Override
	public void runTable(Table table, ITableListener tableListener) {
		if (switchOnActions(table)) {
			domainTraverser.setCurrentAction();
			return;
		}
		if (switchOnChecks(table)) {
			domainTraverser.setCurrentCheck();
			return;
		}
		try {
			for (int rowNo = 0; rowNo < table.size(); rowNo++)
				processRow(table.at(rowNo), tableListener.getTestResults());
		} catch (IgnoredException e) {
			//
		} catch (Exception e) {
			table.error(tableListener, e);
		}
	}

	private boolean switchOnActions(Table table) {
		return domainTraverser != null && table.size() == 1 && table.at(0).size() == 1
				&& table.at(0).at(0).matchesTextInLowerCase("actions", this);
	}

	private boolean switchOnChecks(Table table) {
		return domainTraverser != null && table.size() == 1 && table.at(0).size() == 1
				&& table.at(0).at(0).matchesTextInLowerCase("checks", this);
	}

	public void processRow(Row row, TestResults testResults) {
		for (int i = 0; i < row.size(); i += 2) {
			Cell cell = row.at(i);
			try {
				ICalledMethodTarget target = PlugBoard.lookupTarget.findSetterOnSut(cell.text(this), this);
				Cell nextCell = row.at(i + 1);
				try {
					target.invoke(nextCell, testResults);
				} catch (IgnoredException e) {
					//
				} catch (Exception e) {
					nextCell.error(testResults, e);
				}
			} catch (Exception e) {
				cell.error(testResults, e);
			}
		}
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		table.error(testResults, new RuntimeException("Don't expect to have this called!"));
		return null;
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
