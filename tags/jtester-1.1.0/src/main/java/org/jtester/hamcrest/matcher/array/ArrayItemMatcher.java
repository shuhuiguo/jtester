package org.jtester.hamcrest.matcher.array;

import java.util.Collection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jtester.utility.ArrayHelper;

@SuppressWarnings("rawtypes")
public class ArrayItemMatcher extends BaseMatcher {
	private Matcher matcher = null;

	private ItemMatcherType type = null;

	public ArrayItemMatcher(Matcher matcher, ItemMatcherType type) {
		this.matcher = matcher;
		this.type = type;
	}

	public boolean matches(Object actual) {
		if (actual == null) {
			return false;
		}
		Collection _actual = ArrayHelper.convertToCollection(actual);

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
		matcher.describeTo(description);
	}

	public static enum ItemMatcherType {
		AND {
			@Override
			public String description() {
				return "all item must match regex %s";
			}
		},
		OR {
			@Override
			public String description() {
				return "has item must match regex %s";
			}
		};

		public abstract String description();
	}
}
