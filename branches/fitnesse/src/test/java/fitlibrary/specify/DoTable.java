/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import fit.Parse;
import fit.exception.FitParseException;
import fitlibrary.DoFixture;
import fitlibrary.parser.table.Table;

public class DoTable extends DoFixture {
	public String firstCellStringValue(Table table) {
		return table.stringAt(0,0,0);
	}
	public Table firstCellValue(Table table) {
		return table.tableAt(0,0,0);
	}
	public Table aTable() throws FitParseException {
		return new Table(new Parse("<html><table border=\"1\" cellspacing=\"0\"><tr><td>one</td><td>two</td><td>three</td></tr></table></html>"));
	}
	public Table nullTable() {
		return null;
	}
}
