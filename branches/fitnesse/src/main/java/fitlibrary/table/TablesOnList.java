/*
// * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.table;

import fit.Parse;

public class TablesOnList extends TableElementOnList<Tables, Table> implements Tables {
	public TablesOnList() {
		super("");
	}

	public TablesOnList(Table theTable) {
		this();
		add(theTable);
	}

	public TablesOnList(Tables tables) {
		this();
		addTables(tables);
	}

	public TablesOnList(String tag) {
		super(tag);
	}

	// @Override
	public Tables deepCopy() {
		Tables copy = TableFactory.tables();
		for (Table table : this)
			copy.add(table.deepCopy());
		copy.setLeader(getLeader());
		copy.setTrailer(getTrailer());
		copy.setTagLine(getTagLine());
		return copy;
	}

	public Tables followingTables() {
		return fromAt(1);
	}

	// @Override
	public void addTables(Tables tables) {
		for (Table table : tables)
			add(table);
	}

	@Override
	protected Tables newObject() {
		return new TablesOnList();
	}

	// @Override
	public String report() {
		StringBuilder builder = new StringBuilder();
		toHtml(builder);
		return builder.toString();
	}

	// @Override
	public void print(String heading) {
		System.out.println("---------Tables for " + heading + ":----------");
		System.out.println(toString());
		System.out.println("-------------------");
	}

	// @Override
	public Parse asParse() {
		TableFactory.useOnLists(false);
		try {
			return TableConversion.convert(this).parse();
		} finally {
			TableFactory.pop();
		}
	}

	// @Override
	// public Tables fromTo(int from, int upto) {
	// TablesOnList tables = new TablesOnList();
	// for (int i = from; i < upto; i++)
	// tables.add(at(i));
	// return tables;
	// }
	@Override
	public Tables fromAt(int i) {
		return fromTo(i, size());
	}
}
