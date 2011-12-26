package org.jtester.matcher.array;

import java.util.List;

import org.jtester.helper.ListHelper;

import ext.jtester.hamcrest.BaseMatcher;
import ext.jtester.hamcrest.Description;
import ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class IteratorMatcher extends BaseMatcher {
	private final Matcher[] matchers;

	public IteratorMatcher(Matcher[] matchers) {
		this.matchers = matchers;
	}

	private StringBuffer desc = new StringBuffer();

	public boolean matches(Object actual) {
		if (actual == null) {
			desc.append("the actual object can't be null.");
			return false;
		}
		List array = ListHelper.toList(actual, false);
		int count = array.size();
		if (count < matchers.length) {
			desc.append("size of list(array) must equal to size of matchers");
			return false;
		}
		for (int index = 0; index < count; index++) {
			Object item = array.get(index);
			if (index < matchers.length) {
				currMatcher = matchers[index];
			} else {
				currMatcher = matchers[matchers.length - 1];
			}
			boolean matched = currMatcher.matches(item);
			if (matched == false) {
				return false;
			}
		}
		return true;
	}

	private Matcher currMatcher = null;

	public void describeTo(Description description) {
		description.appendText(desc.toString());
		if (currMatcher != null) {
			currMatcher.describeTo(description);
		}
	}
}
