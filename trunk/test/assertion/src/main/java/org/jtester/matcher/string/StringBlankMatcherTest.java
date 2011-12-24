package org.jtester.matcher.string;

import org.jtester.IAssertion;
import org.jtester.junit.DataFrom;
import org.junit.Test;

import ext.jtester.hamcrest.MatcherAssert;

public class StringBlankMatcherTest implements IAssertion {

	@Test
	@DataFrom("blankString_true")
	public void testMatches_True(String input) {
		StringBlankMatcher matcher = new StringBlankMatcher();
		MatcherAssert.assertThat(input, matcher);
	}

	public static Object[][] blankString_true() {
		return new Object[][] { { "" },// <br>
				{ "   " }, /** <br> */
				{ "\n\t\b\f" } /** <br> */
		};
	}

	@Test
	@DataFrom("blankString_false")
	public void testMatches_False(String input) {
		StringBlankMatcher matcher = new StringBlankMatcher();
		try {
			MatcherAssert.assertThat(input, matcher);
		} catch (Error e) {
			String message = e.getMessage();
			want.string(message).contains("expected is empty string,  but actual", StringMode.IgnoreSpace);
		}
	}

	public static Object[][] blankString_false() {
		return new Object[][] { { null }, // <br>
				{ " d " } /** <br> */
		};
	}
}
