/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.spec;

import java.util.Iterator;

import fit.Parse;
import fit.exception.FitParseException;
import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.spec.filter.FoldFilter;
import fitlibrary.spec.filter.StackTraceFilter;
import fitlibrary.spec.matcher.FitLabelMatcher;
import fitlibrary.spec.matcher.ImageSrcMatcher;
import fitlibrary.spec.matcher.StringMatcher;
import fitlibrary.table.Cell;
import fitlibrary.table.TableElement;
import fitlibrary.table.TableFactory;
import fitlibrary.table.Tables;

public class TablesCompare {
	private final SpecifyErrorReport errorReport;
	private final VariableResolver resolver;
	private final PipeLine matcherPipeline = new FoldFilter(new StackTraceFilter(new FitLabelMatcher(
			new ImageSrcMatcher(new StringMatcher()))));

	public TablesCompare(SpecifyErrorReport errorReport, VariableResolver resolver) {
		this.errorReport = errorReport;
		this.resolver = resolver;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean tablesEqual(String path, TableElement actualInitial, TableElement expectedInitial) {
		TableElement actual = actualInitial;
		TableElement expected = expectedInitial;
		boolean actualContainsHtmlDueToShow = false;
		if (actual instanceof Cell) {
			Cell actualCell = (Cell) actual;
			Cell expectedCell = (Cell) expected;
			if (expectedCell.hasEmbeddedTables(resolver) && !"".equals(actualCell.fullText())
					&& "".equals(expectedCell.fullText())) {
				expectedCell.setText(expectedCell.last().getTrailer());
				expectedCell.last().setTrailer("");
			}
			if (!equals(actualCell.fullText(), expectedCell.fullText())) {
				if (!actualCell.hasEmbeddedTables(resolver) && expectedCell.hasEmbeddedTables(resolver)) {
					try {
						TableFactory.useOnLists(false);
						Tables actualTables = TableFactory.tables(new Parse(actualCell.fullText()));
						TableFactory.pop();
						if (!tablesEqual(path, actualTables, expectedCell.getEmbeddedTables()))
							return false;
						actualContainsHtmlDueToShow = true;
					} catch (FitParseException e) {
						errorReport.cellTextWrong(path, actualCell.fullText(), expectedCell.fullText());
						return false;
					}
				} else {
					errorReport.cellTextWrong(path, actualCell.fullText(), expectedCell.fullText());
					return false;
				}
			}
			actual = actualCell.getEmbeddedTables();
			expected = expectedCell.getEmbeddedTables();
		}
		if (!actualContainsHtmlDueToShow) {
			if ("".equals(expected.getLeader()) && actual.getLeader().equals("<html>")) {
				//
			} else if (!equals(actual.getLeader(), expected.getLeader())) {
				errorReport.leaderWrong(path, actual.getLeader(), expected.getLeader());
				return false;
			}
			if ("".equals(expected.getTrailer()) && actual.getTrailer().equals("</html>")) {
				//
			} else if (!equals(actual.getTrailer(), expected.getTrailer())) {
				errorReport.trailerWrong(path, actual.getTrailer(), expected.getTrailer());
				return false;
			}
			if ("".equals(expected.getTagLine()) && actual.getTagLine().equals("border=\"1\" cellspacing=\"0\"")) {
				//
			} else if (!actual.getTagLine().equals(expected.getTagLine())) {
				errorReport.tagLineWrong(path, actual.getTagLine(), expected.getTagLine());
				return false;
			}
			if (actual.size() != expected.size()) {
				errorReport.sizeWrong(path, actual.size(), expected.size());
				return false;
			}
		}
		Iterator<TableElement> actuals = actual.iterator();
		Iterator<TableElement> expecteds = expected.iterator();
		int count = 0;
		while (actuals.hasNext()) {
			TableElement act = actuals.next();
			String nameOfElement = act.getType() + "[" + count + "]";
			String pathFurther = "".equals(path) ? nameOfElement : path + "." + nameOfElement;
			if (!tablesEqual(pathFurther, act, expecteds.next()))
				return false;
			count++;
		}
		return true;
	}

	public boolean equals(String actualString, String expectedString) {
		String actual = canonical(actualString);
		String expected = canonical(expectedString);
		if ("IGNORE".equals(expected))
			return true;
		return matcherPipeline.match(actual, expected);
	}

	private String canonical(String s) {
		return s.replaceAll("\t", " ").replaceAll("\r", "").replaceAll("<hr>", "").replaceAll("&nbsp;", "")
				.replaceAll("<hr/>", "").replaceAll("<br>", "").replaceAll("<br/>", "").replaceAll("\n", "").trim();
	}

	static class MismatchException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
