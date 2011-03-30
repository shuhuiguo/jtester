/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.spec;

import fitlibrary.table.Tables;

public interface SpecifyErrorReport {
	void sizeWrong(String path, int actualSize, int expectedSize);

	void cellTextWrong(String path, String actualText, String expectedText);

	void leaderWrong(String path, String actualLeader, String expectedLeader);

	void trailerWrong(String path, String actualTrailer, String expectedTrailer);

	void tagLineWrong(String path, String actualTagLine, String expectedTagLine);

	void actualResult(Tables expectedTables);
}
