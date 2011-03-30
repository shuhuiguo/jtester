/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.runner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fit.Parse;
import fitlibrary.utility.ParseUtility;

public class CollectSetUpTearDown extends FileParseUtilities {
	private static final Set<String> SETUPS = new HashSet<String>(Arrays.asList(new String[] { "SETUP.XLS",
			"SETUP.HTML", "SETUP.HTM" }));
	private static final Set<String> TEARDOWNS = new HashSet<String>(Arrays.asList(new String[] { "TEARDOWN.XLS",
			"TEARDOWN.HTML", "TEARDOWN.HTM" }));
	private String encoding;

	public CollectSetUpTearDown(String encoding) {
		this.encoding = encoding;
	}

	public Parse appendSetUp(Parse tables, File theInDiry) throws IOException {
		Parse newTables = copyParse(tables);
		gatherTables(theInDiry, SETUPS, newTables.last());
		return newTables;
	}

	public Parse prependTearDown(Parse tables, File theInDiry) throws IOException {
		Parse newTables = new Parse("", "", null, null);
		gatherTables(theInDiry, TEARDOWNS, newTables);
		ParseUtility.append(newTables, copyParse(tables));
		return newTables.more;
	}

	private Parse gatherTables(File theInDiry, Set<String> matching, Parse endTableInitial)
			throws FileNotFoundException, IOException {
		Parse endTable = endTableInitial;
		File xlsFile = null;
		File htmlFile = null;
		File[] files = theInDiry.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			String name = file.getName().toUpperCase();
			if (matching.contains(name)) {
				if (isXlsFileName(name))
					xlsFile = file;
				else {
					if (htmlFile != null)
						throw new RuntimeException("Can't have .html and .htm files in " + theInDiry.getAbsolutePath());
					htmlFile = file;
				}
				endTable = endTable.last();
			}
		}
		try {
			if (xlsFile != null)
				ParseUtility.append(endTable, new SpreadsheetRunner().collectTable(xlsFile));
		} catch (CustomRunnerException e) {
			ignoreFile(htmlFile, e);
		}
		try {
			if (htmlFile != null)
				ParseUtility.append(endTable, new HtmlRunner().collectTable(htmlFile, encoding));
		} catch (ParseException e) {
			ignoreFile(htmlFile, e);
		}
		return endTable;
	}

	public static boolean specialFileName(String mixedCaseName) {
		String name = mixedCaseName.toUpperCase();
		return SETUPS.contains(name) || TEARDOWNS.contains(name);
	}

}
