package org.jtester.hamcrest.iassert.object.impl;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.hamcrest.text.IsEqualIgnoringWhiteSpace;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringEndsWith;
import org.hamcrest.core.StringStartsWith;
import org.jtester.hamcrest.iassert.common.impl.ComparableAssert;
import org.jtester.hamcrest.iassert.object.intf.IStringAssert;
import org.jtester.hamcrest.mockito.Matches;

public class StringAssert extends ComparableAssert<String, IStringAssert> implements IStringAssert {
	public StringAssert() {
		super(IStringAssert.class);
		this.valueClaz = String.class;
	}

	public StringAssert(String str) {
		super(str, IStringAssert.class);
		this.valueClaz = String.class;
	}

	public IStringAssert contains(String expected) {
		StringContains matcher = new StringContains(expected);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert end(String expected) {
		Matcher<String> matcher = StringEndsWith.endsWith(expected);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert eqIgnoreCase(String string) {
		Matcher<String> matcher = IsEqualIgnoringCase.equalToIgnoringCase(string);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert regular(String regex) {
		Matcher<?> matcher = new Matches(regex);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert start(String expected) {
		Matcher<String> matcher = StringStartsWith.startsWith(expected);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert eqIgnorBlank(String string) {
		Matcher<String> matcher = IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace(string);
		return (IStringAssert) this.assertThat(matcher);
	}

	public IStringAssert notContain(String sub) {
		Matcher<?> _matcher = IsNot.not(new StringContains(sub));
		return this.assertThat(_matcher);
	}

	public IStringAssert notBlank() {
		Matcher<?> matcher = the.string().not(the.string().eqIgnorBlank(""));
		return this.assertThat("expected string is blank", matcher);
	}
}
