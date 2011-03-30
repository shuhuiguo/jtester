package fitlibrary.spec.matcher;

import fitlibrary.spec.PipeLine;
import fitlibrary.spec.filter.Filter;

public abstract class Matcher extends Filter {
	private final String endLabel;

	public Matcher(PipeLine pass, String startLabel, String endLabel) {
		super(pass, startLabel);
		this.endLabel = endLabel;
	}

	@Override
	public boolean matchAfterPrefix(String actual, String expected) {
		int endExpected = expected.indexOf(endLabel);
		int endActual = actual.indexOf(endLabel);
		if (endExpected < 0 || endActual < 0)
			return false;
		if (!matchInside(actual.substring(0, endActual), expected.substring(0, endExpected)))
			return false;
		return match(actual.substring(endActual + endLabel.length()),
				expected.substring(endExpected + endLabel.length()));
	}

	protected abstract boolean matchInside(String actual, String expected);
}
