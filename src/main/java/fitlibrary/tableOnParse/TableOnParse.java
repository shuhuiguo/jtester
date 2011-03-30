/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.tableOnParse;

import fit.Parse;
import fitlibrary.exception.table.MissingRowException;
import fitlibrary.runResults.ITableListener;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;

public class TableOnParse extends TableElementOnParse<Row> implements Table {
	private int firstErrorRow = 0;

	public TableOnParse(Parse parse) {
		super(parse);
	}

	public TableOnParse() {
		super(new Parse("table", "", null, null));
		parse().addToTag(" border=\"1\" cellspacing=\"0\"");
	}

	public TableOnParse(Row... rows) {
		this();
		for (Row row : rows)
			add(row);
	}

	@Override
	public int size() {
		if (parse == null || parse.parts == null)
			return 0;
		return parse.parts.size();
	}

	@Override
	public Row at(int i) {
		if (!atExists(i))
			throw new MissingRowException("");
		return new RowOnParse(parse.parts.at(i));
	}

	@Override
	public void pass(TestResults testResults) {
		at(firstErrorRow).pass(testResults);
	}

	// @Override
	public void ignore(TestResults testResults) {
		at(firstErrorRow).ignore(testResults);
	}

	@Override
	public void error(TestResults testResults, Throwable e) {
		at(firstErrorRow).error(testResults, e);
	}

	// @Override
	public void error(ITableListener tableListener, Throwable e) {
		error(tableListener.getTestResults(), e);
	}

	// @Override
	public TableOnParse add(Row row) {
		if (parse.parts == null)
			parse.parts = row.parse();
		else
			parse.parts.last().more = row.parse();
		return this;
	}

	// @Override
	public void add(int i, Row row) {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public void removeElementAt(int i) {
		throw new RuntimeException("Not implemented");
	}

	// @Override
	public Row newRow() {
		Row row = TableFactory.row();
		add(row);
		return row;
	}

	// @Override
	public int phaseBoundaryCount() {
		int count = (parse.leader).split("<hr>").length - 1;
		if (count == 0)
			count = (parse.leader).split("<hr/>").length - 1;
		return count;
	}

	// @Override
	public void addFoldingText(String fold) {
		if (parse.more != null)
			new TableOnParse(parse.more).addToStartOfLeader(fold);
		else
			addToTrailer(fold);
	}

	public TablesOnParse getTables() {
		return new TablesOnParse(parse);
	}

	/**
	 * Even up all rows by changing the last cells column span of each row to
	 * match
	 * 
	 * DESIGN NOTE: this does not shrink the last cell column span. Sometimes
	 * one has to add rows below a row and only has that row column span to work
	 * with in order to even out the new rows with the previous ones: Example:
	 * |aa|aa|aa| |bb colspan=3|
	 * 
	 * The bb fixture wants to add a few rows but is only given the current row.
	 * So in order to have an even table, the only thing the bb fixture can do
	 * after having added its rows is to do new Table(bbRow).evenUpRows(). all
	 * added rows should at least have a column span of 3. This obviously does
	 * not work if the added rows have more than 3 columns... NOTE: if there is
	 * a better way like getting somehow the handle on the true table (as
	 * opposed to creating one on the fly), please make it shrink the last cells
	 * column span..
	 */
	// @Override
	public void evenUpRows() {
		int maxRowLength = getMaxRowColumnSpan();
		for (Row row : this) {
			row.setColumnSpan(maxRowLength);
		}
	}

	private int getMaxRowColumnSpan() {
		int maxLength = 0;
		for (Row row : this)
			maxLength = Math.max(maxLength, row.getColumnSpan());
		return maxLength;
	}

	// @Override
	public boolean isPlainTextTable() {
		return parse().tag.contains("plain_text_table");
	}

	// @Override
	public void replaceAt(int t, Row row) {
		row.parse().more = parse().parts.at(t).more;
		if (t == 0)
			parse().parts = row.parse();
		else
			parse().parts.at(t - 1).more = row.parse();
	}

	// @Override
	public Table deepCopy() {
		Table copy = TableFactory.table();
		for (Row row : this)
			copy.add(row.deepCopy());
		copy.setLeader(getLeader());
		copy.setTrailer(getTrailer());
		return copy;
	}

	// @Override
	public boolean isEmpty() {
		return parse.more == null;
	}

	// @Override
	public String getType() {
		return "Table";
	}

	// @Override
	public boolean hasRowsAfter(Row currentRow) {
		for (int i = 0; i < size() - 1; i++)
			if (at(i).parse() == currentRow.parse())
				return true;
		return false;
	}

	// @Override
	public Table fromAt(int rowNo) {
		if (rowNo == 0)
			return this;
		return TableFactory.table(at(rowNo));
	}

	// @Override
	public Table fromTo(int from, int upto) {
		Table result = TableFactory.table();
		for (int i = from; i < upto; i++)
			result.add(at(i).deepCopy());
		return result;
	}

	// @Override
	public Table asTableOnParse() {
		return this;
	}
}
