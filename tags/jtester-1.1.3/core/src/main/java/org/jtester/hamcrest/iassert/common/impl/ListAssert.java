package org.jtester.hamcrest.iassert.common.impl;

import java.util.List;
import java.util.Map;

import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IListAssert;
import org.jtester.hamcrest.matcher.array.SizeOrLengthMatcher;
import org.jtester.hamcrest.matcher.array.SizeOrLengthMatcher.SizeOrLengthMatcherType;
import org.jtester.hamcrest.matcher.property.MapListPropertyEqaulMatcher;
import org.jtester.hamcrest.matcher.property.PropertyAllItemsMatcher;
import org.jtester.hamcrest.matcher.property.PropertyAnyItemMatcher;
import org.jtester.hamcrest.matcher.property.ReflectionEqualMatcher;
import org.jtester.hamcrest.matcher.property.reflection.EqMode;
import org.jtester.utility.ListHelper;

import ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class ListAssert<T, E extends IAssert> extends ReflectionAssert<T, E> implements IAssert<T, E>,
		IListAssert<T, E> {
	public ListAssert(Class<? extends IAssert> clazE) {
		super(clazE);
	}

	public ListAssert(T value, Class<? extends IAssert> clazE) {
		super(value, clazE);
	}

	public E isEqualTo(Object expected, EqMode... modes) {
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, modes);
		return this.assertThat(matcher);
	}

	public E eqIgnoreOrder(Object expected) {
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, new EqMode[] { EqMode.IGNORE_ORDER });
		return this.assertThat(matcher);
	}

	public E matchAnyProperty(String property, Matcher matcher) {
		PropertyAnyItemMatcher _matcher = new PropertyAnyItemMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E matchAllProperty(String property, Matcher matcher) {
		PropertyAllItemsMatcher _matcher = new PropertyAllItemsMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	// following are size equal matcher
	public E sizeIs(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.EQ);
		return this.assertThat(matcher);
	}

	public E sizeEq(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.EQ);
		return this.assertThat(matcher);
	}

	public E sizeGe(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.GE);
		return this.assertThat(matcher);
	}

	public E sizeGt(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.GT);
		return this.assertThat(matcher);
	}

	public E sizeLe(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.LE);
		return this.assertThat(matcher);
	}

	public E sizeLt(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.LT);
		return this.assertThat(matcher);
	}

	public E sizeNe(int size) {
		SizeOrLengthMatcher matcher = new SizeOrLengthMatcher(size, SizeOrLengthMatcherType.NE);
		return this.assertThat(matcher);
	}

	@SuppressWarnings("unchecked")
	public E reflectionEqMap(List<Map<String, ?>> expected, EqMode... modes) {
		List _modes = ListHelper.toList(modes);
		if (_modes.contains(EqMode.IGNORE_DEFAULTS) == false) {
			_modes.add(EqMode.IGNORE_DEFAULTS);
		}
		EqMode[] arrays = (EqMode[]) _modes.toArray(new EqMode[0]);
		MapListPropertyEqaulMatcher matcher = new MapListPropertyEqaulMatcher(expected, arrays);
		return this.assertThat(matcher);
	}
}
