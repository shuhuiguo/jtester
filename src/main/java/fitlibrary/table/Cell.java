/*
 * Copyright (c) 2010 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 */

package fitlibrary.table;

import fitlibrary.dynamicVariable.VariableResolver;
import fitlibrary.runResults.TestResults;

public interface Cell extends Tables {
	String text();

	String text(VariableResolver resolver);

	String textLower(VariableResolver resolver);

	String fullText();

	void setText(String text);

	void setUnvisitedEscapedText(String s);

	boolean isBlank(VariableResolver resolver);

	boolean matchesTextInLowerCase(String text, VariableResolver resolver);

	// String camelledText(VariableResolver resolver);

	boolean hasEmbeddedTables(VariableResolver resolver);

	Tables getEmbeddedTables();

	Table getEmbeddedTable();

	void setInnerTables(Tables tables);

	void pass(TestResults testResults);

	void passOrFail(TestResults testResults, boolean right);

	void pass(TestResults testResults, String msg);

	void passIfNotEmbedded(TestResults testResults, VariableResolver resolver);

	void passOrFailIfBlank(TestResults testResults, VariableResolver resolver);

	void fail(TestResults testResults);

	void fail(TestResults testResults, String message, VariableResolver resolver);

	void failWithStringEquals(TestResults testResults, String message, VariableResolver resolver);

	void failHtml(TestResults testResults, String html);

	void wrongHtml(TestResults testResults, String show);

	void expectedElementMissing(TestResults testResults);

	void actualElementMissing(TestResults testResults);

	void actualElementMissing(TestResults testResults, String s);

	void unexpected(TestResults testResults, String string);

	void exceptionExpected(boolean exceptionExpected, Exception e, TestResults testResults);

	void error(TestResults testResults);

	void error(TestResults testResults, String s);

	void ignore(TestResults testResults);

	void shown();

	boolean didPass();

	boolean didFail();

	boolean wasIgnored();

	boolean hadError();

	boolean unresolved(VariableResolver resolver);

	int getColumnSpan();

	void setColumnSpan(int span);

	void setIsHidden();

	void setUnvisitedText(String s);

	void calls();

	void addPrefixToFirstInnerTable(String s);
}
