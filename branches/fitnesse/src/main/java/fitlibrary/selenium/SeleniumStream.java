/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.selenium;

import java.io.FileWriter;
import java.io.Writer;

public class SeleniumStream extends HtmlSeleniumStream {
	public SeleniumStream(Writer writer) {
		super(writer);
	}

	public SeleniumStream(FileWriter writer, int maxColumns) {
		super(writer, maxColumns);
	}

	public void open(String url) {
		printRow("open", url);
	}

	public void clickAndWait(String link) {
		printRow("clickAndWait", link);
	}

	public void clickAndWait(String link, String newLocation) {
		printRow("clickAndWait", link);
		assertLocation(newLocation);
	}

	public void type(String id, String value) {
		printRow("type", id, value);
	}

	public void type(String id, Object object) {
		type(id, object.toString());
	}

	public void verifyLocation(String location) {
		printRow("verifyLocation", location);
	}

	public void assertLocation(String url) {
		printRow("assertLocation", url);
	}

	public void verifyTable(String tableId, int row, int column, String expected) {
		printRow("verifyTable", tableId + "." + row + "." + column, expected);
	}

	public void verifyTable(String tableId, int row, int column, Object expectedObject) {
		verifyTable(tableId, row, column, expectedObject.toString());
	}

	public void verifyTable(String tableId, int row, Object[] expectedValues) {
		for (int column = 0; column < expectedValues.length; column++)
			verifyTable(tableId, row, column, expectedValues[column].toString());
	}

	public void verifyTextPresent(String s) {
		printRow("verifyTextPresent", s);
	}

	public void verifyTitle(String title) {
		printRow("verifyTitle", title);
	}

	public void assertTitle(String title) {
		printRow("assertTitle", title);
	}

	public void verifyValue(String id, String expected) {
		printRow("verifyValue", id, expected);
	}

	public void verifyValue(String id, Object object) {
		verifyValue(id, object.toString());
	}

	public void verifyElementPresent(String element) {
		printRow("verifyElementPresent", element);
	}

	public void link(String url, String title) {
		printRow("<a href=\"" + url + "\">" + title + "</a>");
	}

	public void storeValue(String variable) {
		printRow("storeValue", variable);
	}
}
