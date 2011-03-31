/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.traverse.workflow.special;

import ognl.Ognl;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.IgnoredException;
import fitlibrary.exception.table.ExtraCellsException;
import fitlibrary.exception.table.MissingCellsException;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.traverse.workflow.caller.TwoStageSpecial;
import fitlibrary.utility.option.None;
import fitlibrary.utility.option.Option;
import fitlibrary.utility.option.Some;

public class PrefixSpecialAction {
	// static Logger logger =
	// FitLibraryLogger.getLogger(PrefixSpecialAction.class);
	public enum ShowSyle {
		ORDINARY, ESCAPED, LOGGED
	}

	public enum NotSyle {
		PASSES_ON_EXCEPION, ERROR_ON_EXCEPION
	}

	protected final SpecialActionContext actionContext;

	public PrefixSpecialAction(SpecialActionContext actionContext) {
		this.actionContext = actionContext;
	}

	public TwoStageSpecial check(final Row row) throws Exception {
		if (row.size() <= 2)
			throw new MissingCellsException("Do");
		final ICalledMethodTarget target = actionContext.findMethodFromRow(row, 1, 1);
		final Cell expectedCell = row.last();
		return new TwoStageSpecial() {
			// @Override
			public void run(TestResults testResults) {
				target.invokeAndCheckForSpecial(row.fromAt(2), expectedCell, testResults, row, row.at(0));
			}
		};
	}

	public TwoStageSpecial log(final Row row) throws Exception {
		if (row.size() <= 1)
			throw new MissingCellsException("Do");
		final ICalledMethodTarget target = actionContext.findMethodFromRow(row, 1, 0);
		return new TwoStageSpecial() {
			// @Override
			public void run(TestResults testResults) {
				try {
					Object result = target.invokeForSpecial(row.fromAt(2), testResults, true, row.at(0));
					reportBoolean(result, row.at(1), testResults);
					report(target.getResultString(result));
				} catch (Exception e) {
					row.error(testResults, e);
				}
			}

			private void report(String text) {
				actionContext.logMessage(text);
			}
		};
	}

	protected void reportBoolean(Object result, Cell cell, TestResults testResults) {
		if (result instanceof Boolean)
			if (((Boolean) result).booleanValue())
				cell.pass(testResults);
			else
				cell.fail(testResults);
	}

	private Option<ICalledMethodTarget> getTarget(final Row row) throws Exception {
		if (row.text(2, actionContext).equals("=")) {
			if (row.size() < 4)
				throw new MissingCellsException("Do");
			else if (row.size() > 4)
				throw new ExtraCellsException("");
			return None.none();
		}
		return new Some<ICalledMethodTarget>(actionContext.findMethodFromRow(row, 2, 0));
	}

	public TwoStageSpecial set(final Row row) throws Exception {
		if (row.size() <= 2)
			throw new MissingCellsException("Do");
		final Option<ICalledMethodTarget> optionalTarget = target(row);
		return new TwoStageSpecial() {
			// @Override
			public void run(TestResults testResults) {
				try {
					String variableName = row.text(1, actionContext);
					if (optionalTarget.isSome()) {
						Object result = optionalTarget.get().invokeForSpecial(row.fromAt(3), testResults, true,
								row.at(0));
						actionContext.setDynamicVariable(variableName, result);
					} else if (row.at(3).hasEmbeddedTables(actionContext))
						actionContext.setDynamicVariable(variableName, row.at(3).getEmbeddedTables());
					else
						actionContext.setDynamicVariable(variableName, Ognl.getValue(row.text(3, actionContext), null));
				} catch (IgnoredException e) {
					// No result, so ignore
				} catch (Exception e) {
					row.error(testResults, e);
				}
			}
		};
	}

	private Option<ICalledMethodTarget> target(Row row) throws Exception {
		if (row.size() == 4 && row.text(2, actionContext).equals("to") && row.at(3).hasEmbeddedTables(actionContext))
			return None.none();
		return getTarget(row);
	}

	public TwoStageSpecial setSymbolNamed(final Row row) throws Exception {
		if (row.size() <= 2)
			throw new MissingCellsException("Do");
		final Option<ICalledMethodTarget> optionalTarget = getTarget(row);
		return new TwoStageSpecial() {
			// @Override
			public void run(TestResults testResults) {
				try {
					String variableName = row.text(1, actionContext);
					if (optionalTarget.isSome()) {
						Object result = optionalTarget.get().invokeForSpecial(row.fromAt(3), testResults, true,
								row.at(0));
						actionContext.setFitVariable(variableName, result);
					} else
						actionContext.setFitVariable(variableName, Ognl.getValue(row.text(3, actionContext), null));
				} catch (IgnoredException e) {
					// No result, so ignore
				} catch (Exception e) {
					row.error(testResults, e);
				}
			}
		};
	}
}
