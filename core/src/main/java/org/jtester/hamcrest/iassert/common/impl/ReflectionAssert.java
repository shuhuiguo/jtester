package org.jtester.hamcrest.iassert.common.impl;

import java.util.Map;

import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IReflectionAssert;
import org.jtester.hamcrest.matcher.property.MapPropertyEqaulMatcher;
import org.jtester.hamcrest.matcher.property.PropertiesEqualMatcher;
import org.jtester.hamcrest.matcher.property.PropertyEqualMatcher;
import org.jtester.hamcrest.matcher.property.PropertyItemMatcher;
import org.jtester.hamcrest.matcher.property.ReflectionEqualMatcher;
import org.jtester.hamcrest.matcher.property.reflection.EqMode;

import ext.jtester.hamcrest.Matcher;

@SuppressWarnings("rawtypes")
public class ReflectionAssert<T, E extends IAssert> extends ListHasItemsAssert<T, E> implements IReflectionAssert<E> {

	public ReflectionAssert(Class<? extends IAssert> clazE) {
		super(clazE);
	}

	public ReflectionAssert(T value, Class<? extends IAssert> clazE) {
		super(value, clazE);
	}

	public E propertyEq(String[] properties, Object expected, EqMode... modes) {
		if (expected instanceof Matcher) {
			throw new AssertionError("please use method[propertyMatch(String, Matcher)]");
		}
		PropertiesEqualMatcher matcher = new PropertiesEqualMatcher(expected, properties, modes);
		return this.assertThat(matcher);
	}

	public E reflectionEq(Object expected, EqMode... modes) {
		if (expected instanceof Matcher) {
			throw new AssertionError("please use method[propertyMatch(String, Matcher)]");
		}
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, modes);
		return this.assertThat(matcher);
	}

	public E propertyMatch(String property, Matcher matcher) {
		PropertyItemMatcher _matcher = new PropertyItemMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E propertyEq(String property, Object expected, EqMode... modes) {
		if (expected instanceof Matcher) {
			throw new AssertionError("please use method[propertyMatch(String, Matcher)]");
		}
		PropertyEqualMatcher matcher = new PropertyEqualMatcher(expected, property, modes);
		return this.assertThat(matcher);
	}

	public E eqIgnoreDefault(Object expected) {
		if (expected instanceof Matcher) {
			throw new AssertionError("please use method[propertyMatch(String, Matcher)]");
		}
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, new EqMode[] { EqMode.IGNORE_DEFAULTS });
		return this.assertThat(matcher);
	}

	public E eqIgnoreAll(Object expected) {
		if (expected instanceof Matcher) {
			throw new AssertionError("please use method[propertyMatch(String, Matcher)]");
		}
		ReflectionEqualMatcher matcher = new ReflectionEqualMatcher(expected, new EqMode[] { EqMode.IGNORE_ORDER,
				EqMode.IGNORE_DEFAULTS, EqMode.IGNORE_DATES });
		return this.assertThat(matcher);
	}

	public E reflectionEqWithMap(Map<String, Object> expected, EqMode... modes) {
		MapPropertyEqaulMatcher matcher = new MapPropertyEqaulMatcher(expected, modes);
		return this.assertThat(matcher);
	}
}
