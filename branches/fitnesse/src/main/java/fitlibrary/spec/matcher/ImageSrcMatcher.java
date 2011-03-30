package fitlibrary.spec.matcher;

import fitlibrary.spec.PipeLine;

public class ImageSrcMatcher extends Matcher {
	public ImageSrcMatcher(PipeLine pass) {
		super(pass, "<img src=\"", ">");
	}

	@Override
	protected boolean matchInside(String actual, String expected) {
		String actualLabel = actual.substring(lastSlash(actual));
		String expectedLabel = expected.substring(lastSlash(expected));
		return actualLabel.endsWith(expectedLabel) || expectedLabel.endsWith(actualLabel);
	}

	private int lastSlash(String s) {
		int lastSlash = s.lastIndexOf("/");
		if (lastSlash < 0)
			return 0;
		return lastSlash;
	}
}
