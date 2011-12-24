package org.jtester.matcher.string;

import org.jtester.matcher.string.StringMode;
import org.jtester.matcher.string.StringStartWithMatcher;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ext.jtester.hamcrest.MatcherAssert;

@Test(groups = { "jtester", "assertion" })
public class StringStartWithMatcherTest extends JTester {

	@Test(dataProvider = "endWithDatas")
	public void testMatch(String actual, String expected, boolean doesMatch, StringMode[] modes) {
		StringStartWithMatcher matcher = new StringStartWithMatcher(expected);
		matcher.setStringModes(modes);

		boolean match = matcher.matches(actual);
		want.bool(match).is(doesMatch);
	}

	@DataProvider
	public Object[][] endWithDatas() {
		return new Object[][] {
				{ "", null, false, null },// <br>
				{ "'abc'=====", "\"abc\"", true, new StringMode[] { StringMode.IgnoreQuato } },// <br>
				{ "ABC=====", "aBc", true, new StringMode[] { StringMode.IgnoreCase } },
				{ " ABC =====", "A\tBC", true, new StringMode[] { StringMode.IgnoreSpace } },// <br>
				{ " abc =====", "Abc", false, new StringMode[] { StringMode.IgnoreCase } } };
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_ActualIsNull() {
		MatcherAssert.assertThat(null, new StringStartWithMatcher(""));
	}
}
