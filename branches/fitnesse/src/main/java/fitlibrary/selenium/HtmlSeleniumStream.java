/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.selenium;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class HtmlSeleniumStream {
	protected PrintWriter writer;
	private boolean inTable = false;
	private int maxColumns = 3; // Usually, Selenium uses 3-column tables

	public HtmlSeleniumStream(Writer writer) {
		this.writer = new PrintWriter(writer);
		this.writer.println("<html><head><title>Generated from FitNesse</title></head><body>");
	}

	public HtmlSeleniumStream(FileWriter writer, int maxColumns) {
		this(writer);
		this.maxColumns = maxColumns;
	}

	public void printHeader1(String s) {
		endTable();
		writer.println("<h1>" + s + "</h1>");
	}

	public void printHeader2(String s) {
		endTable();
		writer.println("<h2>" + s + "</h2>");
	}

	public void printHeader3(String s) {
		endTable();
		writer.println("<h3>" + s + "</h3>");
	}

	public void close() {
		endTable();
		writer.println("</body></html>");
		writer.close();
	}

	protected void printRow(String command) {
		startTable();
		if (maxColumns > 1)
			writer.println("<tr><td>" + command + "</td><td></td><td></td></tr>");
		else
			writer.println("<tr><td>" + command + "</td></tr>");
	}

	protected void printRow(String command, String target) {
		startTable();
		writer.println("<tr><td>" + command + "</td><td>" + target + "</td><td></td></tr>");
	}

	protected void printRow(String command, String target, String value) {
		startTable();
		writer.println("<tr><td>" + command + "</td><td>" + target + "</td><td>" + value + "</td></tr>");
	}

	private void startTable() {
		if (!inTable) {
			inTable = true;
			writer.println("<table border=2 cellspacing=0><tbody>");
			if (maxColumns > 1)
				writer.println("<tr><td><i>" + getTableHeader() + "</i></td><td></td><td></td></tr>");
			else
				writer.println("<tr><td><i>" + getTableHeader() + "</i></td></tr>");
		}
	}

	protected String getTableHeader() {
		return "Fit";
	}

	private void endTable() {
		if (inTable)
			writer.println("</tbody></table>");
		inTable = false;
	}
}
