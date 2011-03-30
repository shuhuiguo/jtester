/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.table;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.exception.FitLibraryShowException;
import fitlibrary.exception.table.MissingCellsException;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.runtime.RuntimeContextInternal;
import fitlibrary.tableOnParse.CellOnParse;

public class RowOnList extends TableElementOnList<Row, Cell> implements Row {
	private boolean rowIsHidden = false;

	public RowOnList() {
		super("tr");
	}

	@Override
	public Cell at(int i) {
		if (i < 0 || i >= size())
			throw new MissingCellsException("at " + i);
		return super.at(i);
	}

	// @Override
	public String text(int i, VariableResolver resolver) {
		return at(i).text(resolver);
	}

	// @Override
	public Cell addCell() {
		Cell cell = TableFactory.cell("");
		add(cell);
		return cell;
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

	public Cell lastCell() {
		return last();
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

	// @Override
	public void missing(TestResults testResults) {
		at(0).expectedElementMissing(testResults);
	}

	// @Override
	public void ignore(TestResults testResults) {
		for (int i = 0; i < size(); i++)
			at(i).ignore(testResults);
	}

	// @Override
	public void setIsHidden() {
		this.rowIsHidden = true;
		for (int i = 0; i < size(); i++)
			at(i).setIsHidden();
	}

	// @Override
	public void passKeywords(TestResults testResults) {
		for (int i = 0; i < size(); i += 2)
			at(i).pass(testResults);
	}

	// @Override
	public Row deepCopy() {
		Row copy = TableFactory.row();
		for (int i = 0; i < size(); i++)
			copy.add((Cell) at(i).deepCopy());
		copy.setLeader(getLeader());
		copy.setTrailer(getTrailer());
		copy.setTagLine(getTagLine());
		return copy;
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
	public String getType() {
		return "Row";
	}

	@Override
	protected Row newObject() {
		return new RowOnList();
	}

	private void handleShow(FitLibraryShowException exception) {
		Cell cell = addCell();
		cell.setText(exception.getResult().getHtmlString());
		cell.shown();
	}
}
