/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.tableOnParse;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fit.Fixture;
import fit.Parse;
import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.exception.table.SingleNestedTableExpected;
import fitlibrary.global.PlugBoard;
import fitlibrary.runResults.TestResults;
import fitlibrary.table.Cell;
import fitlibrary.table.Table;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;
import fitlibrary.utility.HtmlUtils;
import fitlibrary.utility.Pair;
import fitlibrary.utility.ParseUtility;

public class CellOnParse extends TablesOnParse implements Cell {
	static final Pattern COLSPAN_PATTERN = Pattern.compile(".*\\b(colspan\\s*=\\s*\"?\\s*(\\d+)\\s*\"?).*");
	private boolean cellIsInHiddenRow = false;

	public CellOnParse(Parse parse) {
		super(parse);
	}

	public CellOnParse(String cellText) {
		this(new Parse("td", cellText, null, null));
	}

	public CellOnParse(Cell cell) {
		this("");
		if (!cell.getEmbeddedTables().isEmpty())
			setInnerTables(cell.getEmbeddedTables());
		else
			setText(cell.fullText());
	}

	public CellOnParse(Tables innerTables) {
		this(new Parse("td", "", innerTables.parse(), null));
	}

	// @Override
	public void setText(String text) {
		parse.body = text;
	}

	// @Override
	public String text(VariableResolver resolver) {
		if (parse.body == null)
			return "";
		Pair<String, Tables> resolve = resolver.resolve(parse.body);
		if (!parse.body.equals(resolve))
			parse.body = resolve.first;
		addTables(resolve.second);
		return text();
	}

	// @Override
	public String text() {
		if (parse.body == null)
			return "";
		return parse.text();
	}

	// @Override
	public boolean unresolved(VariableResolver resolver) {
		return text().startsWith("@{") && text().indexOf("}") == text().length() - 1 && text().equals(text(resolver));
	}

	// @Override
	public String textLower(VariableResolver resolver) {
		return text(resolver).toLowerCase();
	}

	// @Override
	public boolean matchesTextInLowerCase(String s, VariableResolver resolver) {
		return text(resolver).toLowerCase().equals(s.toLowerCase());
	}

	// @Override
	public boolean isBlank(VariableResolver resolver) {
		return text(resolver).equals("");
	}

	@Override
	public CellOnParse deepCopy() {
		return new CellOnParse(ParseUtility.copyParse(parse));
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof CellOnParse))
			return false;
		CellOnParse other = (CellOnParse) object;
		return parse.body.equals(other.parse.body);
	}

	@Override
	public int hashCode() {
		return parse.body.hashCode();
	}

	// @Override
	public void expectedElementMissing(TestResults testResults) {
		fail(testResults);
		addToBody(label("missing"));
	}

	// @Override
	public void actualElementMissing(TestResults testResults) {
		fail(testResults);
		addToBody(label("surplus"));
	}

	// @Override
	public void unexpected(TestResults testResults, String s) {
		fail(testResults);
		addToBody(label("unexpected " + s));
	}

	// @Override
	public void actualElementMissing(TestResults testResults, String value) {
		fail(testResults);
		parse.body = Fixture.gray(Fixture.escape(value.toString()));
		addToBody(label("surplus"));
	}

	@Override
	public void pass(TestResults testResults) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		super.pass(testResults);
	}

	// @Override
	public void pass(TestResults testResults, String msg) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		super.pass(testResults);
		addToBody("<hr>" + Fixture.escape(msg) + label("actual"));
	}

	@Override
	public void fail(TestResults testResults) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		super.fail(testResults);
	}

	// @Override
	public void fail(TestResults testResults, String msg, VariableResolver resolver) {
		if ("".equals(parse.body) && !hasEmbeddedTables(resolver)) {
			failHtml(testResults, msg);
			return;
		}
		fail(testResults);
		String resolved = "";
		if (!text().equals(text(resolver)))
			resolved = " = " + text(resolver);
		addToBody(resolved + label("expected") + "<hr>" + Fixture.escape(msg) + label("actual"));
	}

	// @Override
	public void failWithStringEquals(TestResults testResults, String actual, VariableResolver resolver) {
		if ("".equals(parse.body) && !hasEmbeddedTables(resolver)) {
			failHtml(testResults, actual);
			return;
		}
		fail(testResults);
		String resolved = "";
		if (!text().equals(text(resolver)))
			resolved = " = " + text(resolver);
		addToBody(resolved + label("expected") + "<hr>" + Fixture.escape(actual) + label("actual")
				+ differences(Fixture.escape(text(resolver)), Fixture.escape(actual)));
	}

	public static String differences(String actual, String expected) {
		return PlugBoard.stringDifferencing.differences(actual, expected);
	}

	// @Override
	public void failHtml(TestResults testResults, String msg) {
		fail(testResults);
		addToBody(msg);
	}

	@Override
	public void error(TestResults testResults, Throwable e) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		addToBody(PlugBoard.exceptionHandling.exceptionMessage(e));
		parse.addToTag(ERROR);
		testResults.exception();
	}

	// @Override
	public void error(TestResults testResults, String msg) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		addToBody("<hr/>" + Fixture.label(msg));
		parse.addToTag(ERROR);
		testResults.exception();
	}

	// @Override
	public void error(TestResults testResults) {
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		parse.addToTag(ERROR);
		testResults.exception();
	}

	// @Override
	public void ignore(TestResults testResults) {
		if (parse.tag.contains(CALLS))
			return;
		if (cellIsInHiddenRow)
			System.out.println("Bug: colouring a cell in a hidden table");
		ensureBodyNotNull();
		if (parse.tag.indexOf("class") >= 0)
			throw new RuntimeException("Duplicate cell class in tag. Tag is already: "
					+ parse.tag.substring(1, parse.tag.length() - 2));
		parse.addToTag(IGNORE);
		testResults.ignore();
	}

	// @Override
	public void exceptionExpected(boolean exceptionExpected, Exception e, TestResults testResults) {
		if (exceptionExpected)
			pass(testResults);
		else
			error(testResults, e);
	}

	// @Override
	public Table getEmbeddedTable() {
		TablesOnParse tables = getEmbeddedTables();
		if (tables.size() != 1)
			throw new SingleNestedTableExpected();
		return tables.at(0);
	}

	// @Override
	public void wrongHtml(TestResults counts, String actual) {
		fail(counts);
		addToBody(label("expected") + "<hr>" + actual + label("actual"));
	}

	private void addToBody(String msg) {
		if (parse.parts != null) {
			if (parse.parts.more == null)
				parse.parts.trailer = msg;
			else
				parse.parts.more.leader += msg;
		} else {
			ensureBodyNotNull();
			parse.addToBody(msg);
		}
	}

	public void setEscapedText(String text) {
		setText(Fixture.escape(text));
	}

	public void setMultilineEscapedText(String text) {
		setText(HtmlUtils.escape(text));
	}

	// @Override
	public String fullText() {
		return parse.body;
	}

	// @Override
	public void setUnvisitedEscapedText(String s) {
		setUnvisitedText(Fixture.escape(s));
	}

	public void setUnvisitedMultilineEscapedText(String s) {
		setUnvisitedText(HtmlUtils.escape(s));
	}

	// @Override
	public void setUnvisitedText(String s) {
		setText(Fixture.gray(s));
	}

	// @Override
	public void passOrFailIfBlank(TestResults counts, VariableResolver resolver) {
		if (isBlank(resolver))
			pass(counts);
		else
			fail(counts, "", resolver);
	}

	// @Override
	public void passIfNotEmbedded(TestResults counts, VariableResolver resolver) {
		if (!hasEmbeddedTables(resolver)) // already coloured
			pass(counts);
	}

	// @Override
	public void setIsHidden() {
		this.cellIsInHiddenRow = true;
	}

	// @Override
	public void setInnerTables(Tables tables) {
		parse.parts = tables.parse();
	}

	// @Override
	public int getColumnSpan() {
		Matcher matcher = COLSPAN_PATTERN.matcher(parse.tag);
		int colspan = 1;
		if (matcher.matches())
			colspan = Integer.parseInt(matcher.group(2));
		return colspan;
	}

	// @Override
	public void setColumnSpan(int colspan) {
		if (colspan < 1)
			return;
		Matcher matcher = COLSPAN_PATTERN.matcher(parse.tag);
		if (matcher.matches())
			parse.tag = parse.tag.replace(matcher.group(1), getColspanHtml(colspan));
		else
			parse.addToTag(getColspanHtml(colspan));
	}

	private static String getColspanHtml(int colspan) {
		return " colspan=\"" + colspan + "\"";
	}

	@Override
	public Table at(int i) {
		return getEmbeddedTables().at(i);
	}

	@Override
	public boolean isEmpty() {
		return getEmbeddedTables().isEmpty();
	}

	@Override
	public int size() {
		return getEmbeddedTables().size();
	}

	@Override
	public Iterator<Table> iterator() {
		return getEmbeddedTables().iterator();
	}

	@Override
	public CellOnParse add(Table table) {
		if (parse.parts == null)
			parse.parts = TableFactory.tables(table).parse();
		else
			getEmbeddedTables().add(table);
		return this;
	}

	// @Override
	public TablesOnParse getEmbeddedTables() {
		// if (!hasEmbeddedTables())
		// throw new NestedTableExpectedException();
		return new TablesOnParse(parse.parts);
	}

	// @Override
	public boolean hasEmbeddedTables(VariableResolver resolver) {
		text(resolver);
		return parse.parts != null;
	}

	@Override
	public String getType() {
		return "Cell";
	}

	// @Override
	public void addPrefixToFirstInnerTable(String s) {
		at(0).setLeader(Fixture.label(s) + getLeader());
	}
}
