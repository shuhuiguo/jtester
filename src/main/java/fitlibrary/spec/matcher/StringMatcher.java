package fitlibrary.spec.matcher;

import fitlibrary.spec.PipeLine;

public class StringMatcher implements PipeLine {
	// @Override
	public boolean match(String actual, String expected) {
		return ignoreNonBreakingWhiteSpace(actual, expected) || actual.equals(expected);
	}

	private boolean ignoreNonBreakingWhiteSpace(String actual, String expected) {
		return "".equals(actual) && expected.equals("&nbsp;") || "".equals(expected) && actual.equals("&nbsp;");
	}
}
