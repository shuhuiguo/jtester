package fitlibrary.spec.matcher;

import fitlibrary.spec.PipeLine;

public class FitLabelMatcher extends Matcher {
	public FitLabelMatcher(PipeLine pass) {
		super(pass, "<span class=\"fit_label\">", "</span>");
	}

	@Override
	protected boolean matchInside(String actual, String expected) {
		return actual.startsWith(expected);
	}
}
