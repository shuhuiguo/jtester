package fitlibrary.spec.filter;

import fitlibrary.spec.PipeLine;

public class FoldFilter implements PipeLine {
	private PipeLine pass;

	public FoldFilter(PipeLine pass) {
		this.pass = pass;
	}

	// @Override
	public boolean match(String actual, String expected) {
		return pass.match(ignoreFold(actual), ignoreFold(expected));
	}

	private String ignoreFold(String text) {
		String s = text;
		while (true) {
			int include = s.indexOf("<div class=\"included\">");
			if (include < 0)
				return s;
			int endDiv = s.indexOf("</div></div>");
			if (endDiv < 0)
				return s;
			s = s.substring(0, include) + s.substring(endDiv + "</div></div>".length());
		}
	}
}
