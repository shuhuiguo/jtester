/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.spec.SpecifyErrorReport;
import fitlibrary.spec.TablesCompare;
import fitlibrary.table.Tables;

public class TablesMatcher extends TypeSafeMatcher<Tables> {
	private final Tables expectedTables;
	private final VariableResolver resolver;

	public TablesMatcher(Tables expected, VariableResolver resolver) {
		this.expectedTables = expected;
		this.resolver = resolver;
	}

	@Override
	public boolean matchesSafely(Tables actualTables) {
		SpecifyErrorReport specifyErrorReport = new SpecifyErrorReport() {
			// @Override
			public void actualResult(Tables actualResult) {
				System.out.println("actualResult " + actualResult.toString());
			}

			// @Override
			public void cellTextWrong(String path, String actual, String expected) {
				display("cellTextWrong", path, actual, expected);
			}

			// @Override
			public void leaderWrong(String path, String actual, String expected) {
				display("leaderWrong", path, actual, expected);
			}

			// @Override
			public void sizeWrong(String path, int actual, int expected) {
				System.out.println("sizeWrong at " + path + " Actual: " + actual + " Expected: " + expected);
			}

			// @Override
			public void tagLineWrong(String path, String actual, String expected) {
				display("tagLineWrong", path, actual, expected);
			}

			// @Override
			public void trailerWrong(String path, String actual, String expected) {
				display("trailerWrong", path, actual, expected);
			}

			private void display(String action, String path, String actualText, String expectedText) {
				System.out.println(action + " at " + path + "\nActual:----\n" + actualText + "\nExpected:----\n"
						+ expectedText + "\n----");
			}
		};
		return new TablesCompare(specifyErrorReport, resolver).tablesEqual("tables", actualTables, expectedTables);
	}

	// @Override
	public void describeTo(Description description) {
		description.appendText("a table the same as ").appendValue(expectedTables);
	}

}
