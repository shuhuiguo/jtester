package org.jtester.hamcrest.iassert.common.impl;

import org.hamcrest.Matcher;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.ICollArrayAssert;
import org.jtester.hamcrest.matcher.property.PropertyMatcher;
import org.jtester.hamcrest.matcher.property.PropertiesArrayRefEqMatcher;
import org.jtester.hamcrest.reflection.ReflectionComparatorMode;

@SuppressWarnings({ "rawtypes" })
public class CollArrayAssert<T, E extends IAssert<T, ?>> extends ReflectionAssert<T, E> implements IAssert<T, E>,
		ICollArrayAssert<T, E> {
	public CollArrayAssert(Class<? extends IAssert<?, ?>> clazE) {
		super(clazE);
	}

	public CollArrayAssert(T value, Class<? extends IAssert<?, ?>> clazE) {
		super(value, clazE);
	}

	public E propertyCollectionMatch(String property, Matcher matcher) {
		PropertyMatcher _matcher = new PropertyMatcher(property, matcher);
		return this.assertThat(_matcher);
	}

	public E propertyCollectionRefEq(String[] properties, Object[][] expecteds, ReflectionComparatorMode... modes) {
		PropertiesArrayRefEqMatcher _matcher = new PropertiesArrayRefEqMatcher(properties, expecteds, modes);
		return this.assertThat(_matcher);
	}

	public E propertyCollectionLenientEq(String[] properties, Object[][] expecteds) {
		return propertyCollectionRefEq(properties, expecteds, ReflectionComparatorMode.LENIENT_ORDER);
	}
}
