package org.jtester.hamcrest.iassert.common.impl;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.jmock.Expectations;
import org.jtester.exception.JTesterException;
import org.jtester.hamcrest.TheStyleAssertion;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.matcher.LinkMatcher;
import org.jtester.module.jmockit.ExpectationsUtil;
import org.jtester.reflector.helper.PrimitiveTypeHelper;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class Assert<T, E extends IAssert<T, ?>> extends BaseMatcher<T> implements IAssert<T, E> {
	protected static final TheStyleAssertion the = new TheStyleAssertion();

	protected Class valueClaz = null;

	protected Object value;

	protected AssertType type;

	protected Class<? extends IAssert> assertClaz;

	protected LinkMatcher<?> link;

	public Assert() {
	}

	public Assert(Class<? extends IAssert<?, ?>> clazE) {
		this.value = null;
		this.type = AssertType.Expectations;
		this.link = new LinkMatcher<T>();
		this.assertClaz = clazE;
	}

	public Assert(T value, Class<? extends IAssert<?, ?>> clazE) {
		this.type = AssertType.AssertThat;
		this.value = value;
		this.assertClaz = clazE;
	}

	public Assert(Class<T> clazT, Class<? extends IAssert> clazE) {
		this.type = AssertType.Expectations;
		this.valueClaz = clazT;
		this.assertClaz = clazE;
		this.link = new LinkMatcher<T>();
	}

	public void describeTo(Description description) {
		if (link != null && this.type == AssertType.Expectations) {
			link.describeTo(description);
		} else if (this.value != null) {
			description.appendText(this.value.toString());
		}
	}

	public E assertThat(Matcher matcher) {
		if (this.type == AssertType.AssertThat) {
			MatcherAssert.assertThat(this.value, matcher);
		} else {
			this.link.add(matcher);
		}
		return (E) this;
	}

	public E assertThat(String message, Matcher matcher) {
		if (this.type == AssertType.AssertThat) {
			MatcherAssert.assertThat(message, this.value, matcher);
		} else {
			this.link.add(message, matcher);
		}
		return (E) this;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}

	public boolean matches(Object item) {
		return this.link.matches(item);
	}

	protected static enum AssertType {
		AssertThat, Expectations;
	}

	@Override
	public boolean equals(Object obj) {
		throw new JTesterException("the method can't be used,please use isEqualTo() instead");
	}

	@Override
	public int hashCode() {
		throw new JTesterException("the method can't be used!");
	}

	public T wanted() {
		if (this.type == AssertType.AssertThat) {
			throw new JTesterException("is not an Expectations");
		}

		if (ExpectationsUtil.isJmockitExpectations()) {
			ExpectationsUtil.addArgMatcher(this);
		} else if (ExpectationsUtil.isJmockitVerifications()) {
			ExpectationsUtil.addArgMatcher(this);
		} else {
			org.jmock.Expectations ex = ExpectationsUtil.getExpectations();
			ex.with(this.link);
		}
		return (T) PrimitiveTypeHelper.getPrimitiveDefaultValue(valueClaz);
	}

	public <F> F wanted(Class<F> claz) {
		if (this.type == AssertType.AssertThat) {
			throw new JTesterException("is not an Expectations");
		}
		if (ExpectationsUtil.isJmockitExpectations()) {
			ExpectationsUtil.addArgMatcher(this);
		} else {
			Expectations ex = ExpectationsUtil.getExpectations();
			ex.with(this.link);
		}
		return (F) PrimitiveTypeHelper.getPrimitiveDefaultValue(claz);
	}
}
