package org.jtester.hamcrest.matcher.array;

import java.util.Collection;

import org.jtester.utility.ListHelper;

import ext.jtester.hamcrest.BaseMatcher;
import ext.jtester.hamcrest.Description;
import ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class ListEveryItemMatcher extends BaseMatcher {
	private Matcher matcher = null;

	private ItemMatcherType type = null;

	public ListEveryItemMatcher(Matcher matcher, ItemMatcherType type) {
		this.matcher = matcher;
		this.type = type;
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			return false;
		}
		Collection _actual = ListHelper.toList(actual, true);

		for (Object item : _actual) {
			boolean match = false;
			if (item != null) {
				match = matcher.matches(item);
			}
			if (match == false && type == ItemMatcherType.AND) {
				return false;
			}
			if (match == true && type == ItemMatcherType.OR) {
				return true;
			}
		}
		if (type == ItemMatcherType.AND) {
			return true;
		} else {
			return false;
		}
	}

	public void describeTo(Description description) {
		if (type == ItemMatcherType.AND) {
			description.appendText("all of item is ");
		} else {
			description.appendText("any of item is ");
		}
		description.appendDescriptionOf(matcher);
	}

	public static enum ItemMatcherType {
		AND, OR
	}
}
