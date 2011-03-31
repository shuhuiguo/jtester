/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
*/
package fitlibrary.specify;

import java.text.SimpleDateFormat;
import java.util.Date;

import fit.Fixture;
import fit.Parse;
import fitlibrary.parser.lookup.ParseDelegation;

public class SequenceFixtureFlowUnderTest extends fitlibrary.SequenceFixture {
	public SequenceFixtureFlowUnderTest() {
		super(new SystemUnderTest());
		ParseDelegation.registerParseDelegate(Date.class,new SimpleDateFormat("yyyy/MM/dd HH:mm"));
	}
	public void specialAction(Parse cellsInitial) {
		Parse cells = cellsInitial.more;
		if (cells.text().equals("right"))
			right(cells);
		else if (cells.text().equals("wrong"))
			wrong(cells);
	}
	public void hiddenMethod() {
		//
	}
	public Fixture fixtureObject(int initial) {
		return new MyColumnFixture(initial);
	}
	public static class MyColumnFixture extends fit.ColumnFixture {
		public int x = 0;
		public MyColumnFixture(int initial) {
			x = initial;
		}
		public int getX() {
			return x;
		}
	}
	public Fixture getSlice(int row, int column) {
		return new LocalRowFixture(row,column);
	}
	public static class LocalRowFixture extends fit.RowFixture {
		private Local[][][] rows = {
		  {
			{ new Local("A0a"), new Local("A0b") },
			{ new Local("A1a"), new Local("A1b") },
			{ new Local("A2a"), new Local("A2b") },
			{ new Local("A3a"), new Local("A3b") }
		  },
		  {
			{ new Local("B0a"), new Local("B0b") },
			{ new Local("B1a"), new Local("B1b") },
			{ new Local("B2a"), new Local("B2b") },
			{ new Local("B3a"), new Local("B3b") }
		  }
		};
		private int row, column;
		
		public LocalRowFixture(int row, int column) {
			this.row = row;
			this.column = column;
		}
		@Override
		public Object[] query() throws Exception {
			return rows[row][column];
		}
		@Override
		public Class<?> getTargetClass() {
			return Local.class;
		}
	}
	public static class Local {
		public String s;

		public Local(String s) {
			this.s = s;
		}
	}

}
