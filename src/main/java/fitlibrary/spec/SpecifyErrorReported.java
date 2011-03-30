/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.spec;

import fitlibrary.global.PlugBoard;
import fitlibrary.table.Tables;
import fitlibrary.traverse.ShowAfter;
import fitlibrary.utility.HtmlUtils;

public class SpecifyErrorReported implements SpecifyErrorReport {
	private final ShowAfter showAfter;

	public SpecifyErrorReported(ShowAfter showAfter) {
		this.showAfter = showAfter;
	}

	// @Override
	public void actualResult(Tables actualTables) {
		actualTables.print("actual");
	}

	// @Override
	public void sizeWrong(String path, int actualSize, int expectedSize) {
		showAfterTable("Size differs at " + path + ". Was " + actualSize + ". Expected " + expectedSize);
	}

	// @Override
	public void cellTextWrong(String path, String actual, String expected) {
		showAfterTable("Cell text differs at " + path + wasExpected(actual, expected));
	}

	// @Override
	public void leaderWrong(String path, String actual, String expected) {
		showAfterTable("Leader differs at " + path + wasExpected(actual, expected));
	}

	// @Override
	public void tagLineWrong(String path, String actual, String expected) {
		showAfterTable("Tag line differs at " + path + wasExpected(actual, expected));
	}

	// @Override
	public void trailerWrong(String path, String actual, String expected) {
		showAfterTable("Trailer differs at " + path + wasExpected(actual, expected));
	}

	private String wasExpected(String actualText, String expectedText) {
		return ". <table border=\"1\" cellspacing=\"0\">" + plainRow("Actual", "Expected")
				+ row(actualText, expectedText) + optionalEscapedRow(actualText, expectedText)
				+ row("" + actualText.length(), "" + expectedText.length()) + "</table>";
	}

	private String optionalEscapedRow(String actualText, String expectedText) {
		String actualEscaped = escape(actualText);
		String expectedEscaped = escape(expectedText);
		if (actualEscaped.equals(actualText) && expectedEscaped.equals(expectedText))
			return "";
		return row(actualEscaped, expectedEscaped);
	}

	private String row(String actual, String expected) {
		return "<tr><td>" + actual + PlugBoard.stringDifferencing.differences(actual, expected) + "</td><td>"
				+ expected + "</td></tr>\n";
	}

	private String plainRow(String actual, String expected) {
		return "<tr><td>" + actual + "</td><td>" + expected + "</td></tr>\n";
	}

	private String escape(String text) {
		return HtmlUtils.escape(text);
	}

	private void showAfterTable(String s) {
		showAfter.showAsAfterTable("Logs", s);
		System.out.println(s);
	}
}
