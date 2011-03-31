/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.specify;

import java.awt.Point;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fit.Fixture;
import fitlibrary.DoFixture;
import fitlibrary.runResults.TestResults;
import fitlibrary.specify.eg.MyPoint;
import fitlibrary.table.Cell;
import fitlibrary.table.Row;

public class DoFixtureFlowUnderTest extends DoFixture {
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public DoFixtureFlowUnderTest() {
		super(new SystemUnderTest());
		registerParseDelegate(Date.class, DATE_FORMAT);
	}

	public MyPoint xY(int x, int y) {
		return new MyPoint(x, y);
	}

	public MyPoint copyAPoint(MyPoint point) {
		return point;
	}

	public void specialAction(Row row, TestResults testResults) {
		Cell cell = row.at(1);
		String text = cell.text(this);
		if (text.equals("right"))
			cell.pass(testResults);
		else if (text.equals("wrong"))
			cell.fail(testResults);
	}

	public boolean column() {
		throw new RuntimeException("Ambiguity with fit.ColumnFixture");
	}

	public boolean Action(Row row, TestResults testResults) {
		throw new RuntimeException("Ambiguity of special action with fit.ActionFixture");
	}

	public void hiddenMethod() {
		//
	}

	public Object aPoint() {
		return new Point(2, 3);
	}

	public class DateHolder {
		public Date date;

		public DateHolder(Date date) {
			this.date = date;
		}
	}

	public void getException() {
		throw new RuntimeException("Forced exception");
	}

	public Integer anInteger() {
		return new Integer(23);
	}

	public Fixture getSlice(int row, int column) {
		return new LocalRowFixture(row, column);
	}

	public static class LocalRowFixture extends fit.RowFixture {
		private Local[][][] rows = {
				{ { new Local("A0a"), new Local("A0b") }, { new Local("A1a"), new Local("A1b") },
						{ new Local("A2a"), new Local("A2b") }, { new Local("A3a"), new Local("A3b") } },
				{ { new Local("B0a"), new Local("B0b") }, { new Local("B1a"), new Local("B1b") },
						{ new Local("B2a"), new Local("B2b") }, { new Local("B3a"), new Local("B3b") } } };
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

	public PointHolder getPointHolder() {
		return new PointHolder();
	}

	public static class PointHolder {
		public Point getPoint() {
			return new Point(24, 7);
		}
	}

	public List<Object> copyOfListOfEntity(List<Object> entities) {
		return entities;
	}

	public Entity entity(String s) {
		return new Entity(s);
	}

	public Entity findEntity(String s) {
		return entity(s);
	}

	public String showEntity(Entity s) {
		return s.toString();
	}

	public static class Entity {
		private String s;

		public Entity(String s) {
			this.s = s;
		}

		public Entity getEntity() {
			return this;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Entity))
				return false;
			return s.equals(((Entity) obj).s);
		}

		@Override
		public String toString() {
			return s;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}
}
