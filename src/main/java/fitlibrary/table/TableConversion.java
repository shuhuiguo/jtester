/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.table;

public class TableConversion {
	public static Tables convert(Tables tables) {
		Tables result = TableFactory.tables();
		for (Table table : tables) {
			result.add(convert(table));
		}
		return result;
	}

	public static Table convert(Table table) {
		Table result = TableFactory.table();
		result.setLeader(table.getLeader());
		result.setTrailer(table.getTrailer());
		result.setTagLine(table.getTagLine());
		for (Row row : table) {
			result.add(convert(row));
		}
		return result;
	}

	public static Row convert(Row row) {
		Row result = TableFactory.row();
		result.setLeader(row.getLeader());
		result.setTrailer(row.getTrailer());
		result.setTagLine(row.getTagLine());
		for (Cell cell : row) {
			result.add(convert(cell));
		}
		return result;
	}

	public static Cell convert(Cell cell) {
		Cell result = TableFactory.cell(cell.fullText());
		result.setLeader(cell.getLeader());
		result.setTrailer(cell.getTrailer());
		result.setTagLine(cell.getTagLine());
		for (Table table : cell) {
			result.add(convert(table));
		}
		return result;
	}
}
