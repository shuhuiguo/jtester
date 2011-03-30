/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.parser.graphic;

import fitlibrary.differences.LocalFile;
import fitlibrary.parser.HtmlStructureParser;
import fitlibrary.parser.Parser;
import fitlibrary.parser.lookup.ParserFactory;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.traverse.Evaluator;
import fitlibrary.traverse.Traverse;
import fitlibrary.typed.Typed;
import fitlibrary.typed.TypedObject;

/**
 * A Parser that handles Graphic images
 */
public class GraphicParser extends HtmlStructureParser {
	public GraphicParser(Typed typed) {
		super(typed);
	}

	public static boolean applicableType(Class<?> type) {
		return GraphicInterface.class.isAssignableFrom(type);
	}

	protected Object parse(String imageLink) {
		Object[] args = new Object[] { getImageFileName(imageLink) };
		Class<?>[] argTypes = new Class[] { LocalFile.class };
		return callReflectively("parseGraphic", args, argTypes, null);
	}

	// @Override
	public String show(Object object) {
		if (object == null)
			return "null";
		LocalFile localFile = (LocalFile) callReflectively("toGraphic", new Object[] {}, new Class[] {}, object);
		String htmlImageLink = localFile.htmlImageLink();
		return htmlImageLink;
	}

	public LocalFile getImageFileName(String html) {
		String match = "src=\"";
		int srcPos = html.indexOf(match);
		if (srcPos < 0)
			throw new RuntimeException("Not a valid graphic link: '" + html + "'");
		int start = srcPos + match.length();
		int end = html.indexOf("\"", start);
		String fileName = html.substring(start, end);
		return Traverse.getLocalFile(fileName);
	}

	@Override
	public Object parse(Cell cell, TestResults testResults) throws Exception {
		return parse(cell.fullText());
	}

	public static ParserFactory parserFactory() {
		return new ParserFactory() {
			// @Override
			public Parser parser(Evaluator evaluator, Typed typed) {
				return new GraphicParser(typed);
			}
		};
	}

	// @Override
	public Evaluator traverse(TypedObject typedObject) {
		throw new RuntimeException("No Traverse available");
	}
}
