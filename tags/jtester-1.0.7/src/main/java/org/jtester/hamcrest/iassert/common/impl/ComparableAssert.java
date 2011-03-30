package org.jtester.hamcrest.iassert.common.impl;

import org.hamcrest.Matcher;
import org.hamcrest.core.AllOf;
import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IComparableAssert;
import org.jtester.hamcrest.mockito.GreaterOrEqual;
import org.jtester.hamcrest.mockito.GreaterThan;
import org.jtester.hamcrest.mockito.LessOrEqual;
import org.jtester.hamcrest.mockito.LessThan;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ComparableAssert<T extends Comparable<T>, E extends IAssert<T, ?>> extends AllAssert<T, E> implements
		IComparableAssert<T, E> {

	public ComparableAssert(Class<? extends IAssert<?, ?>> clazE) {
		super(clazE);
	}

	public ComparableAssert(T value, Class<? extends IAssert<?, ?>> clazE) {
		super(value, clazE);
	}

	public E greaterEqual(T min) {
		GreaterOrEqual matcher = new GreaterOrEqual(min);
		return this.assertThat(matcher);
	}

	public E greaterThan(T min) {
		GreaterThan matcher = new GreaterThan(min);
		return this.assertThat(matcher);
	}

	public E lessEqual(T max) {
		LessOrEqual matcher = new LessOrEqual(max);
		return this.assertThat(matcher);
	}

	public E lessThan(T max) {
		LessThan matcher = new LessThan(max);
		return this.assertThat(matcher);
	}

	public E between(T min, T max) {
		if (min.compareTo(max) > 0) {
			throw new AssertionError(String.format("arg1[%s] must less than arg2[%s]", min, max));
		}
		GreaterOrEqual geq = new GreaterOrEqual(min);
		LessOrEqual leq = new LessOrEqual(max);
		Matcher<?> matcher = AllOf.allOf(geq, leq);
		return this.assertThat(matcher);
	}
}
