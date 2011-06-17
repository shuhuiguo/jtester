package org.jtester.hamcrest.iassert.common.impl;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIn;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IBaseAssert;
import org.jtester.hamcrest.matcher.clazz.ClassAssignFromMatcher;
import org.jtester.hamcrest.mockito.And;
import org.jtester.hamcrest.mockito.Any;
import org.jtester.hamcrest.mockito.NotNull;
import org.jtester.hamcrest.mockito.Null;
import org.jtester.hamcrest.mockito.Or;
import org.jtester.hamcrest.mockito.Same;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseAssert<T, E extends IAssert<T, ?>> extends Assert<T, E> implements IAssert<T, E>, IBaseAssert<T, E> {
	public BaseAssert() {
		super();
	}

	public BaseAssert(Class<? extends IAssert<?, ?>> clazE) {
		super(clazE);
	}

	public BaseAssert(T value, Class<? extends IAssert<?, ?>> clazE) {
		super(value, clazE);
	}

	public E isEqualTo(T expected) {
		Matcher<? super T> matcher = IsEqual.equalTo(expected);
		return this.assertThat(matcher);
	}

	public E isEqualTo(String message, T expected) {
		Matcher<? super T> matcher = IsEqual.equalTo(expected);
		return this.assertThat(message, matcher);
	}

	public E notEqualTo(T expected) {
		Matcher<? super T> matcher = IsNot.not(IsEqual.equalTo(expected));
		return this.assertThat(matcher);
	}

	public E clazIs(Class expected) {
		Matcher<?> matcher = Is.is(expected);
		return this.assertThat(matcher);
	}

	public E clazAssignFrom(Class claz) {
		ClassAssignFromMatcher matcher = new ClassAssignFromMatcher(claz);
		return this.assertThat(matcher);
	}

	public E is(Matcher matcher) {
		Matcher<T> _matcher = Is.is(matcher);
		return this.assertThat(_matcher);
	}

	public E not(Matcher matcher) {
		Matcher<T> _matcher = IsNot.not(matcher);
		return this.assertThat(_matcher);
	}

	public E allOf(Matcher matcher1, Matcher matcher2, Matcher... matchers) {
		List<Matcher> list = list(matcher1, matcher2, matchers);
		// Matcher<?> matcher = AllOf.allOf(list);
		Matcher matcher = new And(list);
		return this.assertThat(matcher);
	}

	public E allOf(Iterable<? extends Matcher> matchers) {
		List<Matcher> list = list(matchers);
		// Matcher<?> matcher = AllOf.allOf(list);
		Matcher matcher = new And(list);
		return this.assertThat(matcher);
	}

	public E anyOf(Matcher matcher1, Matcher matcher2, Matcher... matchers) {
		List<Matcher> list = list(matcher1, matcher2, matchers);
		// Matcher<?> matcher = AnyOf.anyOf(list);
		Matcher matcher = new Or(list);
		return this.assertThat(matcher);
	}

	private List<Matcher> list(Matcher matcher1, Matcher matcher2, Matcher... matchers) {
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(matcher1);
		list.add(matcher2);
		if (matchers != null) {
			for (Matcher matcher : matchers) {
				list.add(matcher);
			}
		}
		return list;
	}

	private List<Matcher> list(Iterable<? extends Matcher> matchers) {
		List<Matcher> list = new ArrayList<Matcher>();
		if (matchers != null) {
			for (Matcher matcher : matchers) {
				list.add(matcher);
			}
		}
		return list;
	}

	public E anyOf(Iterable<? extends Matcher> matchers) {
		List<Matcher> list = list(matchers);
		// Matcher matcher = AnyOf.anyOf(list);
		Matcher matcher = new Or(list);
		return this.assertThat(matcher);
	}

	public E in(T... values) {
		Matcher<T> matcher = IsIn.isOneOf(values);
		return this.assertThat(matcher);
	}

	public E notIn(T... values) {
		Matcher<T> _matcher = IsNot.not(IsIn.isOneOf(values));
		return this.assertThat(_matcher);
	}

	public E same(T value) {
		Matcher<?> _matcher = new Same(value);
		return this.assertThat(_matcher);
	}

	public E any() {
		Matcher<?> _matcher = Any.ANY;
		return this.assertThat(_matcher);
	}

	public E isNull() {
		Matcher<?> _matcher = Null.NULL;
		return this.assertThat(_matcher);
	}

	public E isNull(String message) {
		Matcher<?> _matcher = Null.NULL;
		return this.assertThat(message, _matcher);
	}

	public E notNull() {
		Matcher<?> _matcher = NotNull.NOT_NULL;
		return this.assertThat(_matcher);
	}

	public E notNull(String message) {
		Matcher<?> _matcher = NotNull.NOT_NULL;
		return this.assertThat(message, _matcher);
	}
}
