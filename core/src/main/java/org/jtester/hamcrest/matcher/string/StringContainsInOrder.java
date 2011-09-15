package org.jtester.hamcrest.matcher.string;

import ext.jtester.hamcrest.Description;
import ext.jtester.hamcrest.Factory;
import ext.jtester.hamcrest.Matcher;
import ext.jtester.hamcrest.TypeSafeMatcher;

public class StringContainsInOrder extends TypeSafeMatcher<String> {
    private final Iterable<String> substrings;

    public StringContainsInOrder(Iterable<String> substrings) {
        this.substrings = substrings;
    }
    
    @Override
    public boolean matchesSafely(String s) {
        int fromIndex = 0;
        
        for (String substring : substrings) {
            fromIndex = s.indexOf(substring, fromIndex);
            if (fromIndex == -1) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText("was \"").appendText(item).appendText("\"");
    }
    
    public void describeTo(Description description) {
        description.appendText("a string containing ")
                   .appendValueList("", ", ", "", substrings)
                   .appendText(" in order");
    }
    
    @Factory
    public static Matcher<String> stringContainsInOrder(Iterable<String> substrings) {
        return new StringContainsInOrder(substrings);
    }
}
