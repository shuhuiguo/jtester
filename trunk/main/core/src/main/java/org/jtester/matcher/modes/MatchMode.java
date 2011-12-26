package org.jtester.matcher.modes;

public enum MatchMode {
	/**
	 * the items(any or all, specified by {@link ItemsMode}) should match all
	 * matchers.
	 */
	MatchAny,
	/**
	 * the items(any or all, specified by {@link ItemsMode}) should match any
	 * matchers.
	 */
	MatchAll;
}