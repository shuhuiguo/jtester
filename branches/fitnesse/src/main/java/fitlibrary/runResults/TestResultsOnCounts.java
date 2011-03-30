/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */
package fitlibrary.runResults;

import fit.Counts;

public class TestResultsOnCounts implements TestResults {
	public static final String PASS_COLOUR = "#cfffcf";
	public static final String FAIL_COLOUR = "#ffcfcf";
	public static final String IGNORE_COLOR = "#efefef";
	public static final String ERROR_COLOUR = "#ffffcf";
	protected final Counts counts;

	public TestResultsOnCounts(Counts counts) {
		this.counts = counts;
	}

	public TestResultsOnCounts() {
		this(new Counts());
	}

	// @Override
	public void pass() {
		counts.right++;
	}

	// @Override
	public void fail() {
		counts.wrong++;
	}

	// @Override
	public void exception() {
		counts.exceptions++;
	}

	// @Override
	public void ignore() {
		counts.ignores++;
	}

	// @Override
	public void clear() {
		counts.right = 0;
		counts.wrong = 0;
		counts.ignores = 0;
		counts.exceptions = 0;
	}

	// @Override
	public void add(TestResults otherResults) {
		counts.tally(otherResults.getCounts());
	}

	// @Override
	public boolean passed() {
		return counts.right > 0 && counts.wrong == 0 & counts.exceptions == 0;
	}

	// @Override
	public boolean failed() {
		return counts.wrong > 0;
	}

	// @Override
	public boolean errors() {
		return counts.exceptions > 0;
	}

	// @Override
	public boolean problems() {
		return counts.wrong + counts.exceptions > 0;
	}

	@Override
	public String toString() {
		return counts.toString();
	}

	// @Override
	public boolean matches(String rights, String wrongs, String ignores, String exceptions) {
		return cellValue(rights) == counts.right && cellValue(wrongs) == counts.wrong
				&& cellValue(ignores) == counts.ignores && cellValue(exceptions) == counts.exceptions;
	}

	private int cellValue(String s) {
		return Integer.parseInt(s);
	}

	// @Override
	public Counts getCounts() {
		return counts;
	}

	public String asHtmlTable() {
		String colour = IGNORE_COLOR;
		if (passed())
			colour = PASS_COLOUR;
		else if (problems())
			if (counts.exceptions > 0)
				colour = ERROR_COLOUR;
			else
				colour = FAIL_COLOUR;
		return "<html><table><tr><td " + backgroundColour(colour) + ">" + counts.toString()
				+ "</td></tr></table><html>";
	}

	public static String replaceCss(String html) {
		String result = html;
		result = result.replaceAll("class=\"pass\"", backgroundColour(PASS_COLOUR));
		result = result.replaceAll("class=\"fail\"", backgroundColour(FAIL_COLOUR));
		result = result.replaceAll("class=\"ignore\"", backgroundColour(IGNORE_COLOR));
		result = result.replaceAll("class=\"error\"", backgroundColour(ERROR_COLOUR));
		return result;
	}

	private static String backgroundColour(String colour) {
		return "bgcolor=\"" + colour + "\"";
	}

	// @Override
	public void addRights(int extraRight) {
		counts.right += extraRight;
	}
}
