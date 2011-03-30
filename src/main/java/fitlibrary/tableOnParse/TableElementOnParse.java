/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.tableOnParse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fit.Parse;
import fitlibrary.runResults.TestResults;

public abstract class TableElementOnParse<To> {
	public final static String PASS = " class=\"pass\"";
	public final static String FAIL = " class=\"fail\"";
	public final static String IGNORE = " class=\"ignore\"";
	public final static String ERROR = " class=\"error\"";
	public final static String SHOWN = " bgcolor=#C0C0FF";
	public final static String CALLS = " bgcolor=#DADAFF";
	protected Parse parse;

	public TableElementOnParse(Parse parse) {
		this.parse = parse;
	}

	public Parse parse() {
		return parse;
	}

	public Parse asParse() {
		return parse;
	}

	public void pass(TestResults testResults) {
		ensureBodyNotNull();
		parse.addToTag(PASS);
		testResults.pass();
	}

	public void fail(TestResults testResults) {
		ensureBodyNotNull();
		if (!hadError()) {
			parse.addToTag(FAIL);
			testResults.fail();
		}
	}

	public void passOrFail(TestResults testResults, boolean right) {
		if (right)
			pass(testResults);
		else
			fail(testResults);
	}

	public void shown() {
		ensureBodyNotNull();
		parse.addToTag(SHOWN);
	}

	public void calls() {
		ensureBodyNotNull();
		parse.addToTag(CALLS);
	}

	public static String label(String string) {
		return " <span class=\"fit_label\">" + string + "</span>";
	}

	public boolean didPass() {
		return tagContains(PASS);
	}

	public boolean didFail() {
		return tagContains(FAIL);
	}

	public boolean wasIgnored() {
		return tagContains(IGNORE);
	}

	public boolean hadError() {
		return tagContains(ERROR);
	}

	protected void ensureBodyNotNull() {
		if (parse.body == null)
			parse.body = "";
	}

	private boolean tagContains(String label) {
		return parse.tag.indexOf(label) >= 0;
	}

	public String getLeader() {
		if (parse == null)
			return "";
		String leader = parse.leader;
		if (leader == null)
			return "";
		return leader;
	}

	public String getTrailer() {
		if (parse == null)
			return "";
		String trailer = parse.trailer;
		if (trailer == null)
			return "";
		return trailer;
	}

	protected void addToTrailer(String s) {
		if (parse.trailer == null)
			parse.trailer = "";
		parse.trailer = s + parse.trailer;
	}

	protected void addToStartOfLeader(String s) {
		parse.leader = s + parse.leader;
	}

	public void setLeader(String leader) {
		parse.leader = leader;
	}

	public void setTrailer(String trailer) {
		parse.trailer = trailer;
	}

	public boolean atExists(int i) {
		return i >= 0 && i < size();
	}

	public To last() {
		return at(size() - 1);
	}

	public List<To> iterableFrom(int start) {
		List<To> list = new ArrayList<To>();
		for (int i = start; i < size(); i++)
			list.add(at(i));
		return list;
	}

	public Iterator<To> iterator() {
		return iterableFrom(0).iterator();
	}

	public String getTagLine() {
		if (parse == null)
			return "";
		String tagLine = parse.tag;
		int index = tagLine.indexOf(" ");
		if (index < 0)
			return "";
		return tagLine.substring(index + 1, tagLine.length() - 1);
	}

	public void setTagLine(String tagLine) {
		if ("".equals(tagLine))
			parse.tag = "<" + getTag() + ">";
		else
			parse.tag = "<" + getTag() + " " + tagLine + ">";
	}

	private String getTag() {
		int index = parse.tag.indexOf(" ");
		if (index < 0) // Eg "<row>"
			return parse.tag.substring(1, parse.tag.length() - 1);
		// Eg "<table columnsize=2>"
		return parse.tag.substring(1, index);
	}

	public void addToTag(String annotation) {
		parse.addToTag(" " + annotation.trim());
	}

	@SuppressWarnings("rawtypes")
	public void toHtml(StringBuilder builder) {
		boolean everything = this.getClass() != TablesOnParse.class;
		if (everything) {
			builder.append(getLeader());
			if (parse.tag.length() > 1)
				builder.append(parse.tag);
			if (parse.body != null)
				builder.append(parse.body);
		}
		for (int i = 0; i < size(); i++)
			((TableElementOnParse) at(i)).toHtml(builder);
		if (everything) {
			builder.append(parse.end);
			builder.append(getTrailer());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		toHtml(builder);
		return builder.toString();
	}

	protected abstract To at(int i);

	protected abstract int size();

	protected abstract void error(TestResults testResults, Throwable e);
}
