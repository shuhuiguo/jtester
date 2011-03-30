/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.tableOnParse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fit.Parse;
import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.table.MissingCellsException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.TableFactory;

public class RowOnParse extends TableElementOnParse<Cell> implements Row {
	private boolean rowIsHidden = false;

	public RowOnParse(Parse parse) {
		super(parse);
	}

	public RowOnParse() {
		this(new Parse("tr", "", null, null));
	}

	@Override
	public int size() {
		if (parse.parts == null)
			return 0;
		return parse.parts.size();
	}

	@Override
	public Cell at(int i) {
		if (!atExists(i))
			throw new MissingCellsException("");
		return new CellOnParse(parse.parts.at(i));
	}

	@Override
	public void pass(TestResults testResults) {
		if (rowIsHidden)
			System.out.println("Bug: colouring a cell in a hidden table");
		super.pass(testResults);
	}

	@Override
	public void fail(TestResults testResults) {
		if (rowIsHidden)
			System.out.println("Bug: colouring a cell in a hidden table");
		super.fail(testResults);
	}

	@Override
	public void error(TestResults testResults, Throwable throwable) {
		Throwable e = PlugBoard.exceptionHandling.unwrapThrowable(throwable);
		if (e instanceof FitLibraryShowException)
			handleShow((FitLibraryShowException) e);
		else
			at(0).error(testResults, e);
	}

	private void handleShow(FitLibraryShowException exception) {
		Cell cell = addCell();
		cell.setText(exception.getResult().getHtmlString());
		cell.shown();
	}

	// @Override
	public String text(int i, VariableResolver resolver) {
		return at(i).text(resolver);
	}

	// @Override
	public void missing(TestResults testResults) {
		at(0).expectedElementMissing(testResults);
	}

	// @Override
	public Cell addCell() {
		Cell cell = TableFactory.cell("");
		add(cell);
		return cell;
	}

	// @Override
	public RowOnParse add(Cell cell) {
		if (rowIsHidden)
			System.out.println("Bug: Adding a cell to a hidden row in a table");
		if (parse.parts == null)
			parse.parts = cell.parse();
		else
			parse.parts.last().more = cell.parse();
		return this;
	}

	// @Override
	public Cell addCell(String text) {
		Cell cell = TableFactory.cell(text);
		add(cell);
		return cell;
	}

	// @Override
	public Cell addCell(String text, int cols) {
		Cell cell = new CellOnParse(text);
		cell.setColumnSpan(cols);
		add(cell);
		return cell;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RowOnParse))
			return false;
		RowOnParse other = (RowOnParse) object;
		if (other.size() != size())
			return false;
		for (int i = 0; i < size(); i++)
			if (!at(i).equals(other.at(i)))
				return false;
		return true;
	}

	// @Override
	public void ignore(TestResults testResults) {
		for (int i = 0; i < size(); i++)
			at(i).ignore(testResults);
	}

	// @Override
	public boolean isEmpty() {
		return size() == 0;
	}

	// @Override
	public void setIsHidden() {
		this.rowIsHidden = true;
		for (int i = 0; i < size(); i++)
			at(i).setIsHidden();
	}

	public Cell lastCell() {
		return at(size() - 1);
	}

	public void addCommentRow(Cell cell) {
		RowOnParse commentRow = new RowOnParse();
		commentRow.addCell("note");
		commentRow.add(cell);
		Parse next = parse.more;
		parse.more = commentRow.parse;
		commentRow.parse.more = next;
	}

	// @Override
	public Row fromAt(int rowNo) {
		return TableFactory.row(at(rowNo));
	}

	// @Override
	public Row fromTo(int from, int upto) {
		Row row = TableFactory.row();
		for (int i = from; i < upto; i++)
			row.add(TableFactory.cell(at(i)));
		return row;
	}

	// @Override
	public void passKeywords(TestResults testResults) {
		for (int i = 0; i < size(); i += 2)
			at(i).pass(testResults);
	}

	// @Override
	public Row deepCopy() {
		Row rowCopy = TableFactory.row();
		for (int i = 0; i < size(); i++)
			rowCopy.add(TableFactory.cell(at(i).fullText()));
		return rowCopy;
	}

	// @Override
	public void clear() {
		parse.parts = null;
	}

	// @Override
	public String methodNameForPlain(RuntimeContextInternal runtime) {
		String name = "";
		for (int i = 0; i < size(); i += 2) {
			name += text(i, runtime);
			if ((i + 1) < size())
				name += "|";
		}
		return name;
	}

	// @Override
	public String methodNameForCamel(RuntimeContextInternal runtime) {
		String name = "";
		for (int i = 0; i < size(); i += 2)
			name += text(i, runtime) + " ";
		return runtime.extendedCamel(name.trim());
	}

	// @Override
	public int argumentCount() {
		return size() / 2;
	}

	public boolean hasFurtherRows() {
		return parse.more != null;
	}

	// @Override
	public int getColumnSpan() {
		int col = 0;
		for (int i = 0; i < size(); i++)
			col += at(i).getColumnSpan();
		return col;
	}

	// @Override
	public void setColumnSpan(int span) {
		if (isEmpty())
			addCell();
		lastCell().setColumnSpan(span - getColumnSpan() + lastCell().getColumnSpan());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	// @Override
	public void removeElementAt(int i) {
		if (i == 0)
			parse.parts = parse.parts.more;
		else {
			at(i - 1).parse().more = null;
			at(i - 1).parse().trailer = "";
		}
	}

	@Override
	public Iterator<Cell> iterator() {
		List<Cell> list = new ArrayList<Cell>();
		for (int i = 0; i < size(); i++)
			list.add(at(i));
		return list.iterator();
	}

	// @Override
	public String getType() {
		return "Row";
	}
}
