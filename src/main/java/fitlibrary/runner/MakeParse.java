/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runner;

import java.io.PrintWriter;
import java.util.StringTokenizer;

import fit.Parse;
import fitlibrary.utility.ParseUtility;

/**
 * Help with creating a Parse for Fit. This needs to be tidied up.
 */
public class MakeParse {
	private static final String NO_TABLE_STARTED = "No table started";
	private Parse tables = null;
	private Parse currentTable = null;
	private Parse currentRow = null;
	private String webPageTitle;

	public MakeParse(String webPageTitle) {
		this.webPageTitle = webPageTitle;
	}

	public void addTable(String[] table) throws CustomRunnerException {
		if (table.length == 0)
			throw new CustomRunnerException("Table needs at least one row.");
		addTable(table[0]);
		for (int i = 1; i < table.length; i++)
			addRow(table[i]);
	}

	public void addTable(String firstRow) throws CustomRunnerException {
		addTableWithLeaderText(firstRow, "");
	}

	public void addTableWithLeaderText(String firstRow, String leader) throws CustomRunnerException {
		addTable(getCellValues(firstRow), leader);
	}

	public void addTable(String[] cells, String leader) throws CustomRunnerException {
		startNewTable(new Parse("table", "", null, null));
		currentTable.addToTag(" border cellspacing=0 cellpadding=3");
		currentTable.leader += leader;
		if (tables != null)
			currentTable.leader += "<br>";
		addRow(cells);
	}

	public void addTables(Parse table) {
		if (currentTable != null)
			endTable();
		if (tables == null) {
			tables = table;
			ParseUtility.changeHeader(tables, header());
		} else
			ParseUtility.append(tables, table);
	}

	public void addTablesWithoutChangingHeader(Parse table) {
		if (currentTable != null)
			endTable();
		if (tables == null)
			tables = table;
		else
			ParseUtility.append(tables, table);
	}

	public void addTableTrailerWithoutBreaks(String text) {
		addTableTrailer(text, "");
	}

	public void addTableTrailer(String text) {
		addTableTrailer(text, "<br>");
	}

	private void addTableTrailer(String text, String breakString) {
		Parse table = currentTable;
		if (currentTable == null) {
			if (tables == null)
				throw new CustomRunnerException(NO_TABLE_STARTED);
			table = tables.last();
		}
		if (table.trailer == null || table.trailer.equals(""))
			table.trailer = "" + text;
		else {
			int index = table.trailer.indexOf(ParseUtility.END_BODY);
			if (index < 0)
				table.trailer += breakString + text;
			else
				table.trailer = table.trailer.substring(0, index) + breakString + text + table.trailer.substring(index);
		}
	}

	public Parse addRow(String cells) throws CustomRunnerException {
		return addRow(getCellValues(cells));
	}

	public Parse addRow(String[] cells) throws CustomRunnerException {
		if (currentTable == null)
			throw new CustomRunnerException(NO_TABLE_STARTED);
		if (currentRow != null)
			endRow();
		currentRow = new Parse("tr", "", null, null);
		for (int i = 0; i < cells.length; i++)
			addCell(cells[i]);
		return currentRow;
	}

	public Parse getTables() {
		endTables();
		return tables;
	}

	public void print(PrintWriter report) {
		endTables();
		tables.print(report);
	}

	public void print(String title) {
		endTables();
		ParseUtility.printParse(tables, title);
	}

	@Override
	public String toString() {
		endTables();
		return ParseUtility.toString(tables);
	}

	private void startNewTable(Parse table) {
		if (currentTable != null)
			endTable();
		currentTable = table;
	}

	private void endTable() throws CustomRunnerException {
		if (currentTable == null)
			throw new CustomRunnerException(NO_TABLE_STARTED);
		if (currentRow != null)
			endRow();
		if (currentTable.parts == null)
			throw new CustomRunnerException("Table has no rows");
		if (tables == null)
			tables = currentTable;
		else
			ParseUtility.append(tables, currentTable);
		colSpan(currentTable);
		currentTable = null;
		currentRow = null;
	}

	private void colSpan(Parse table) {
		int length = 0;
		Parse rows = table.parts;
		while (rows != null) {
			length = Math.max(length, rows.parts.size());
			rows = rows.more;
		}
		rows = table.parts;
		while (rows != null) {
			int span = length - rows.parts.size() + 1;
			if (span > 1)
				rows.parts.last().addToTag(" ColSpan=" + span);
			rows = rows.more;
		}
	}

	private String[] getCellValues(String cells) {
		int count = 0;
		for (StringTokenizer st = new StringTokenizer(cells, ",|"); st.hasMoreTokens(); st.nextToken())
			count++;
		String[] row = new String[count];
		int i = 0;
		for (StringTokenizer st = new StringTokenizer(cells, ",|"); st.hasMoreTokens(); i++)
			row[i] = st.nextToken();
		return row;
	}

	private void endRow() {
		if (currentRow.parts == null)
			throw new CustomRunnerException("Row is empty");
		addToParts(currentTable, currentRow);
		currentRow = null;
	}

	private void addToParts(Parse node, Parse part) {
		if (node.parts == null)
			node.parts = part;
		else
			node.parts.last().more = part;
	}

	private void addCell(String body) throws CustomRunnerException {
		if (currentTable == null)
			throw new CustomRunnerException(NO_TABLE_STARTED);
		if (currentRow == null)
			throw new CustomRunnerException("No row started");
		Parse td = new Parse("td", body, null, null);
		addToParts(currentRow, td);
	}

	private void endTables() throws CustomRunnerException {
		if (currentTable != null)
			endTable();
		if (tables == null)
			throw new CustomRunnerException("No table included");
		ParseUtility.changeHeader(tables, header());
		ParseUtility.completeTrailer(tables);
	}

	private String header() {
		return "<html><head><title>" + webPageTitle + "</title></head><body>";
	}
}
