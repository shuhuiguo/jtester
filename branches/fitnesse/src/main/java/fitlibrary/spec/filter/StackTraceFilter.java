package fitlibrary.spec.filter;

import fitlibrary.spec.PipeLine;

public class StackTraceFilter extends Filter {
	public StackTraceFilter(PipeLine pass) {
		super(pass, "class=\"fit_stacktrace\">");
	}

	@Override
	public boolean matchAfterPrefix(String actual, String expected) {
		return true; // No more to do.
	}
}
