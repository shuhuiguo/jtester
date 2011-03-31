/*
 * Copyright (c) 2009 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fit.Parse;
import fit.exception.FitParseException;
import fitlibrary.exception.FitLibraryException;
import fitlibrary.table.TableConversion;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.tableOnParse.TablesOnParse;

public class SimpleWikiTranslator {
	private final FileAccess fileAccess;

	public SimpleWikiTranslator(FileAccess fileAccess) {
		this.fileAccess = fileAccess;
	}

	public void translate(HtmlReceiver receiver) {
		Iterator<File> files = fileAccess.filesWithSuffix("txt");
		while (files.hasNext()) {
			File file = files.next();
			translateLines(file, fileAccess.linesOf(file), receiver);
		}
	}

	public static Tables translateToTables(String wiki) throws FitParseException {
		return new TablesOnParse(new Parse(translate(wiki)));
	}

	public static Tables translateToTablesOnList(String wiki) throws FitParseException {
		Tables tablesOnParse = translateToTables(wiki);
		TableFactory.useOnLists(true);
		Tables tables = TableConversion.convert(tablesOnParse);
		TableFactory.pop();
		return tables;
	}

	public static String translate(String wiki) {
		final StringBuilder result = new StringBuilder();
		HtmlReceiver accumulatingReceiver = new HtmlReceiver() {
			// @Override
			public void take(File file, String html) {
				result.append(html);
			}
		};
		NullIterator<String> lines = new NullIterator<String>(Arrays.asList(wiki.split("\n")).iterator());
		translateLines(new File(""), lines, accumulatingReceiver);
		return result.toString();
	}

	private static void translateLines(File file, NullIterator<String> lines, HtmlReceiver receiver) {
		StringBuilder result = new StringBuilder("<html>\n<br/>");
		String line = lines.next();
		while (true) {
			line = processText(line, lines, result);
			if (lines.end(line))
				break;
			line = processTable(line, lines, result);
			if (lines.end(line))
				break;
		}
		result.append("</html>");
		receiver.take(file, ParseUtility.tabulize(result.toString()));
	}

	private static String processText(String lineOriginal, NullIterator<String> lines, StringBuilder result) {
		String line = lineOriginal;
		while (true) {
			if (lines.end(line))
				return line;
			if (line.startsWith("!"))
				line = line.substring(1);
			if (line.startsWith("#")) {
				// ignore it
			} else if (line.startsWith("|!contents|"))
				result.append("<br/>\n");
			else if (line.startsWith("|"))
				return line;
			else if (line.startsWith("----"))
				result.append("<hr/>\n");
			else
				result.append(clearWikiJunk(line) + "<br/>\n");
			line = lines.next();
		}
	}

	private static String clearWikiJunk(String lineOriginal) {
		return lineOriginal.replaceAll("'''", "").replaceAll("''", "").replaceAll("!-", "").replaceAll("-!", "");
	}

	private static String processTable(String lineOriginal, NullIterator<String> lines, StringBuilder result) {
		String line = lineOriginal;
		result.append("<table border=\"1\" cellspacing=\"0\">\n");
		while (true) {
			if (lines.end(line))
				break;
			if (!line.startsWith("|"))
				break;
			result.append("<tr>");
			List<String> cells = split(line);
			for (String cell : cells)
				result.append("<td>" + clearWikiJunk(cell) + "</td>");
			result.append("</tr>\n");
			line = lines.next();
		}
		result.append("</table>\n<br/>");
		return line;
	}

	public static List<String> split(String s) {
		ArrayList<String> result = new ArrayList<String>();
		int lastBar = 1;
		while (true) {
			int bar = s.indexOf("|", lastBar);
			if (bar < 0)
				break;
			int lastEscape = lastBar;
			while (true) {
				int escape = s.indexOf("!-", lastEscape);
				if (escape < 0 || escape > bar)
					break;
				int endEscape = s.indexOf("-!", escape);
				if (endEscape < 0)
					throw new FitLibraryException("Missing -!");
				lastEscape = endEscape;
				bar = s.indexOf("|", endEscape);
			}
			if (bar == lastBar)
				result.add("");
			else
				result.add(s.substring(lastBar, bar).trim());
			lastBar = bar + 1;
		}
		return result;
	}
}
