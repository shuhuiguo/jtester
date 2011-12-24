package org.jtester.matcher.string;

import org.jtester.IAssertion;
import org.jtester.junit.DataFrom;
import org.junit.Test;

import ext.jtester.hamcrest.MatcherAssert;

public class IgnoreAllSpaceMatcherTest implements IAssertion {

	@Test
	@DataFrom("spaceMatcherData")
	public void testMatches(String expected, String actual, boolean doesMatch) {
		StringMatcher matcher = new StringEqualMatcher(expected);
		matcher.setStringModes(StringMode.IgnoreSpace);

		boolean match = matcher.matches(actual);
		want.bool(match).isEqualTo(doesMatch);
	}

	public static Object[][] spaceMatcherData() {
		return new Object[][] { { "", "", true },// <br>
				{ null, "", false }, /** <br> */
				{ "\n\t\b\f", "", true }, /** <br> */
				{ " d ", "d", true } /** <br> */
		};
	}

	@Test(expected = AssertionError.class)
	public void testMatches_ActualIsNull() {
		MatcherAssert.assertThat(null, new StringEqualMatcher(""));
	}
}
