package org.jtester.matcher.string;

import org.jtester.IAssertion;
import org.jtester.junit.DataFrom;
import org.junit.Test;

import ext.jtester.hamcrest.MatcherAssert;

public class StringContainMatcherTest implements IAssertion {

	@Test
	@DataFrom("dataForStringContains")
	public void testMatches(String actual, String expected, boolean doesMatch, StringMode[] modes) {
		StringContainMatcher matcher = new StringContainMatcher(new String[] { expected }, modes);

		boolean match = matcher.matches(actual);
		want.bool(match).is(doesMatch);
	}

	public static Object[][] dataForStringContains() {
		return new Object[][] {
				{ "", null, false, null },// <br>
				{ "'abc' \"ABCD\"", "'abcd'", true, new StringMode[] { StringMode.IgnoreCase, StringMode.SameAsQuato } },// <br>
				{ " abc \t\n abcc ", "c abc", true, new StringMode[] { StringMode.SameAsSpace } },// <br>
				{ "'abc' \"ABCD\"", "'abcd'", false, new StringMode[] { StringMode.IgnoreCase } },// <br>
				{ " abc \t\n abcc ", "c abc", false, new StringMode[] { StringMode.IgnoreCase } } // <br>}
		};
	}

	@Test(expected = AssertionError.class)
	public void testMatches_ActualIsNull() {
		MatcherAssert.assertThat(null, new StringContainMatcher(new String[] { "" }, null));
	}
}
