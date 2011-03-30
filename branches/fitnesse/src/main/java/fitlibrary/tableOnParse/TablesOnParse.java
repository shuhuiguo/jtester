/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.tableOnParse;

import fit.Parse;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.ParseUtility;

public class TablesOnParse extends TableElementOnParse<Table> implements Tables {

	public TablesOnParse(Parse parse) {
		super(parse);
	}

	public TablesOnParse() {
		super(null);
	}

	public TablesOnParse(Table theTable) {
		this(theTable.parse());
	}

	public TablesOnParse(Tables tables) {
		this(ParseUtility.copyParse(tables.parse()));
	}

	@Override
	public Table at(int i) {
		return new TableOnParse(parse.at(i));
	}

	// @Override
	public TablesOnParse add(Table table) {
		if (parse == null)
			parse = table.parse();
		else
			parse.last().more = table.parse();
		return this;
	}

	@Override
	public int size() {
		if (parse == null)
			return 0;
		return parse.size();
	}

	// @Override
	public Tables deepCopy() {
		Tables copy = TableFactory.tables();
		for (Table table : this)
			copy.add(table.deepCopy());
		return copy;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TablesOnParse))
			return false;
		TablesOnParse other = (TablesOnParse) obj;
		if (size() != other.size())
			return false;
		for (int i = 0; i < size(); i++)
			if (!at(i).equals(other.at(i)))
				return false;
		return true;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	// @Override
	public Tables fromAt(int i) {
		return TableFactory.tables(at(i));
	}

	// @Override
	public Tables fromTo(int from, int upto) {
		Tables result = TableFactory.tables();
		for (int i = from; i < upto; i++)
			result.add(at(i).deepCopy());
		return result;
	}

	public Tables followingTables() {
		return new TablesOnParse(parse.more);
	}

	// @Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void error(TestResults testResults, Throwable e) {
		at(0).error(testResults, e);
	}

	// @Override
	public String getType() {
		return "Tables";
	}

	// @Override
	public String report() {
		return ParseUtility.toString(parse());
	}

	// @Override
	public void addTables(Tables tables) {
		for (Table table : tables)
			add(table);
	}

	// @Override
	public void print(String heading) {
		ParseUtility.printParse(parse(), "actual");
	}
}
