/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.matcher;

import org.jmock.Expectations;
import org.jmock.Mockery;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;
import fitlibrary.table.Table;
import fitlibrary.table.Tables;
import fitlibrary.utility.option.None;
import fitlibrary.utility.option.Option;
import fitlibrary.utility.option.Some;

public class TableBuilderForTests {
	public static class TablesBuilder extends TableElementBuilder<Tables, TableBuilder, Table> {
		public TablesBuilder() {
			super(Tables.class);
		}

		public TablesBuilder with(TableBuilder... els) {
			TablesBuilder fresh = new TablesBuilder();
			with(fresh, els);
			return fresh;
		}
	}

	public static class TableBuilder extends TableElementBuilder<Table, RowBuilder, Row> {
		public TableBuilder() {
			super(Table.class);
		}

		public TableBuilder with(RowBuilder... els) {
			TableBuilder fresh = new TableBuilder();
			with(fresh, els);
			return fresh;
		}

		public TableBuilder withLeader(String lead) {
			TableBuilder fresh = new TableBuilder();
			withLeader(fresh, lead);
			return fresh;
		}

		public TableBuilder withTrailer(String trail) {
			TableBuilder fresh = new TableBuilder();
			withTrailer(fresh, trail);
			return fresh;
		}
	}

	public static class RowBuilder extends TableElementBuilder<Row, CellBuilder, Cell> {
		public RowBuilder() {
			super(Row.class);
		}

		public RowBuilder with(CellBuilder... els) {
			RowBuilder fresh = new RowBuilder();
			with(fresh, els);
			return fresh;
		}

		public RowBuilder withLeader(String lead) {
			RowBuilder fresh = new RowBuilder();
			withLeader(fresh, lead);
			return fresh;
		}
	}

	public static class CellBuilder extends TableElementBuilder<Cell, TableBuilder, Table> {
		protected Option<String> optionalText = None.none();

		public CellBuilder() {
			super(Cell.class);
		}

		public CellBuilder(Option<String> optionalText) {
			super(Cell.class);
			this.optionalText = optionalText;
		}

		public CellBuilder(String text) {
			this(new Some<String>(text));
		}

		@Override
		public Cell mock(final Mockery context, final String path, final int index) {
			final Cell cell = super.mock(context, path, index);
			final String text = optionalText.isSome() ? optionalText.get() : localPath(path, index);
			context.checking(new Expectations() {
				{
					allowing(cell).text();
					will(returnValue(text));
					allowing(cell).fullText();
					will(returnValue(text));
					allowing(cell).text(with(any(VariableResolver.class)));
					will(returnValue(text));
					allowing(cell).hasEmbeddedTables(with(any(VariableResolver.class)));
					will(returnValue(!cell.isEmpty()));
					allowing(cell).getEmbeddedTables();
					will(returnValue(cell));
				}
			});
			return cell;
		}

		public CellBuilder with(TableBuilder... els) {
			CellBuilder fresh = new CellBuilder(optionalText);
			with(fresh, els);
			return fresh;
		}

		public CellBuilder withTagLine(String tagLiner) {
			CellBuilder fresh = new CellBuilder(optionalText);
			withTagLine(fresh, tagLiner);
			return fresh;
		}
	}

	public static TablesBuilder tables() {
		return new TablesBuilder();
	}

	public static TableBuilder table() {
		return new TableBuilder();
	}

	public static RowBuilder row() {
		return new RowBuilder();
	}

	public static CellBuilder cell() {
		return new CellBuilder();
	}

	public static CellBuilder cell(String text) {
		return new CellBuilder(text);
	}
}
