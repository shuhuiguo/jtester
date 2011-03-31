/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.traverse.function;

import java.util.ArrayList;
import java.util.List;

import fitlibrary.closure.CalledMethodTarget;
import fitlibrary.closure.ICalledMethodTarget;
import fitlibrary.exception.FitLibraryExceptionWithHelp;
import fitlibrary.exception.method.VoidMethodException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.typed.TypedObject;

/**
 * A traverse similar in function to ColumnFixture, except that: o It separates
 * the given and calculated columns by an empty column o It doesn't treat any
 * strings as special by default o Special strings can be defined for repeats
 * and exception-expected. o A single method call is made for each expected
 * column, rather than using public instance variables for the givens, as with
 * ColumnFixture. With the header row: |g1 |g2 ||e1 |e2 | |1.1|1.2||2.3|0.1|
 * Each row will lead to a call of the following methods with two (given) double
 * arguments: e1G1G2() and e2G1G2() o As with WorkflowTraverse, a
 * systemUnderTest (SUT) may be associated with the domain object and any method
 * calls not available on the domain object are called on the SUT.
 * 
 * See the FitLibrary specifications for examples
 */
public class CalculateTraverse extends FunctionTraverse {
	private ICalledMethodTarget[] targets;
	private int methods = 0;
	private boolean notesPermitted = false;
	private boolean hasNotes = false;
	protected int argCount = -1;
	protected boolean boundOK = false;

	public CalculateTraverse() {
		//
	}

	public CalculateTraverse(Object sut) {
		setSystemUnderTest(sut);
	}

	public CalculateTraverse(TypedObject typedObject) {
		setTypedSystemUnderTest(typedObject);
	}

	@Override
	public Object interpretAfterFirstRow(Table table, TestResults testResults) {
		methods = 0;
		bindFirstRowToTarget(table.at(1), testResults);
		for (int i = 2; i < table.size(); i++)
			processRow(table.at(i), testResults);
		return null;
	}

	public void processRow(Row row, TestResults testResults) {
		if (!boundOK) {
			row.ignore(testResults);
			return;
		}
		if (hasNotes) {
			if (row.size() < argCount + methods + 1) {
				row.error(testResults, new FitLibraryExceptionWithHelp("Row should be at least "
						+ (argCount + methods + 1) + " cells wide", "CalculateRowTooShort"));
				return;
			}
		} else if (row.size() != argCount + methods + 1) {
			row.error(testResults, new FitLibraryExceptionWithHelp("Row should be " + (argCount + methods + 1)
					+ " cells wide", "CalculateRowWrongLength"));
			return;
		}
		for (int i = 0; i < methods; i++)
			targets[i].invokeAndCheck(row, row.at(i + argCount + 1), testResults, true);
	}

	public void bindFirstRowToTarget(Row row, TestResults testResults) {
		boolean pastDoubleColumn = false;
		int rowLength = row.size();
		String argNames = "";
		List<String> arguments = new ArrayList<String>();
		for (int i = 0; i < rowLength; i++) {
			Cell cell = row.at(i);
			String name = cell.text(this);
			try {
				if (name.equals("")) {
					if (pastDoubleColumn && notesPermitted) {
						hasNotes = true;
						break;
					}
					argCount = i;
					arguments.add("arg" + (i + 1));
					targets = new CalledMethodTarget[rowLength - i - 1];
					pastDoubleColumn = true;
				} else {
					notesPermitted = true;
					if (pastDoubleColumn) {
						String methodName = extendedCamel(name + argNames);
						if (arguments.size() > argCount) // Blank separating
															// column is not an
															// arg
							arguments.remove(arguments.size() - 1);
						ICalledMethodTarget target = PlugBoard.lookupTarget.findMethodOrGetter(methodName, arguments,
								"TypeOfResult", this);
						if (target.returnsVoid())
							throw new VoidMethodException(methodName, "CalculateTraverse");
						targets[methods] = target;
						methods++;
						target.setRepeatAndExceptionString(repeatString, exceptionString);
					} else {
						arguments.add(runtimeContext.extendedCamel(name));
						argNames += " " + name;
					}
				}
			} catch (Exception e) {
				cell.error(testResults, e);
				return;
			}
		}
		if (methods == 0)
			row.error(testResults, new FitLibraryExceptionWithHelp("No calculated column", "NoCalculateColumn"));
		boundOK = true;
	}
}
