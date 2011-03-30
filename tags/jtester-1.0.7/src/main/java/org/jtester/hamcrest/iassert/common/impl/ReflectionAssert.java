package org.jtester.hamcrest.iassert.common.impl;

import org.hamcrest.Matcher;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IReflectionAssert;
import org.jtester.hamcrest.matcher.property.AllPropertiesMatcher;
import org.jtester.hamcrest.matcher.property.HasPropertyMatcher;
import org.jtester.hamcrest.matcher.property.PropertiesArrayMatcher;
import org.jtester.hamcrest.matcher.property.PropertiesArrayRefEqMatcher;
import org.jtester.hamcrest.matcher.property.PropertyMatcher;
import org.jtester.hamcrest.matcher.property.UnitilsPropertyMatcher;
import org.jtester.hamcrest.matcher.property.UnitilsReflectionMatcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;

@SuppressWarnings("rawtypes")
public class ReflectionAssert<T, E extends IAssert<T, ?>> extends ObjectContainerAssert<T, E> implements
		IReflectionAssert<E> {

	public ReflectionAssert(Class<? extends IAssert<?, ?>> clazE) {
		super(clazE);
	}

	public ReflectionAssert(T value, Class<? extends IAssert<?, ?>> clazE) {
		super(value, clazE);
	}

	public E reflectionEq(Object expected, ReflectionComparatorMode... modes) {
		UnitilsReflectionMatcher matcher = new UnitilsReflectionMatcher(expected, modes);
		return this.assertThat(matcher);
	}

	public E lenientEq(Object expected) {
		UnitilsReflectionMatcher matcher = new UnitilsReflectionMatcher(expected, new ReflectionComparatorMode[] {
				ReflectionComparatorMode.IGNORE_DEFAULTS, ReflectionComparatorMode.LENIENT_ORDER });
		return this.assertThat(matcher);
	}

	public E propertyEq(String property, Object expected) {
		if (expected instanceof Matcher) {
			return propertyMatch(property, (Matcher) expected);
		} else {
			UnitilsPropertyMatcher matcher = new UnitilsPropertyMatcher(property, expected, null);
			return this.assertThat(matcher);
		}
	}

	public E hasPropertyMatch(String property, Matcher matcher) {
		HasPropertyMatcher _matcher = new HasPropertyMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E allPropertyMatch(String property, Matcher matcher) {
		AllPropertiesMatcher _matcher = new AllPropertiesMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E propertyEq(String[] properties, Object[] expected) {
		PropertiesArrayRefEqMatcher _matcher = new PropertiesArrayRefEqMatcher(properties, new Object[][] { expected });
		return this.assertThat(_matcher);
	}

	public E propertyMatch(String property, Matcher matcher) {
		PropertyMatcher _matcher = new PropertyMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E propertiesMatch(String[] properties, Object[] matchers) {
		PropertiesArrayMatcher matcher = new PropertiesArrayMatcher(properties, matchers);
		return this.assertThat(matcher);
	}
}
