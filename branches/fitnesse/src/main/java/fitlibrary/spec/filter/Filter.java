package fitlibrary.spec.filter;

import fitlibrary.spec.PipeLine;

public abstract class Filter implements PipeLine {
	protected PipeLine pass;
	private String startLabel;

	public Filter(PipeLine pass, String startLabel) {
		this.pass = pass;
		this.startLabel = startLabel;
	}

	// @Override
	public boolean match(String actual, String expected) {
		int startExpected = expected.indexOf(startLabel);
		int startActual = actual.indexOf(startLabel);
		if (startExpected < 0 && startActual < 0)
			return pass.match(actual, expected);
		if (startExpected < 0 && startActual >= 0 || startExpected >= 0 && startActual < 0)
			return false;
		if (!pass.match(actual.substring(0, startActual), expected.substring(0, startExpected)))
			return false;
		return matchAfterPrefix(actual.substring(startActual + startLabel.length()),
				expected.substring(startExpected + startLabel.length()));
	}

	protected abstract boolean matchAfterPrefix(String actual, String expected);
}
