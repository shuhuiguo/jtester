package org.jtester.hamcrest.iassert.common.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jtester.hamcrest.iassert.common.intf.IAssert;
import org.jtester.hamcrest.iassert.common.intf.IListHasItemsAssert;
import org.jtester.hamcrest.matcher.array.ListEveryItemMatcher;
import org.jtester.hamcrest.matcher.array.ListEveryItemMatcher.ItemMatcherType;
import org.jtester.hamcrest.matcher.mockito.Matches;
import org.jtester.utility.ArrayHelper;

import ext.jtester.hamcrest.Matcher;
import ext.jtester.hamcrest.collection.IsArrayContaining;
import ext.jtester.hamcrest.core.AllOf;
import ext.jtester.hamcrest.core.IsCollectionContaining;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ListHasItemsAssert<T, E extends IAssert> extends BaseAssert<T, E> implements IListHasItemsAssert<E> {

	public ListHasItemsAssert(Class<? extends IAssert> clazE) {
		super(clazE);
	}

	public ListHasItemsAssert(T value, Class<? extends IAssert> clazE) {
		super(value, clazE);
	}

	public E hasItems(Object item, Object... items) {
		Matcher matcher1 = this.getHasItemMatcher(item);
		if (items == null || items.length == 0) {
			return this.assertThat(matcher1);
		}
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(matcher1);
		for (Object temp : items) {
			Matcher matcher2 = this.getHasItemMatcher(temp);
			list.add(matcher2);
		}
		Matcher matcher = AllOf.allOf(list);
		return this.assertThat(matcher);
	}

	public E hasItems(Object[] values) {
		if (values == null || values.length == 0) {
			return (E) this;
		}
		List<Matcher> list = new ArrayList<Matcher>();
		for (Object item : values) {
			list.add(this.getHasItemMatcher(item));
		}
		return assertThat(AllOf.allOf(list));
	}

	public E hasItems(Collection collection) {
		if (collection == null || collection.size() == 0) {
			return (E) this;
		}
		List<Matcher> list = new ArrayList<Matcher>();
		for (Object item : collection) {
			list.add(this.getHasItemMatcher(item));
		}
		return assertThat(AllOf.allOf(list));
	}

	private Matcher getHasItemMatcher(Object item) {
		assert valueClaz != null : "the value asserted must not be null";
		if (this.valueClaz == Object[].class) {
			return IsArrayContaining.hasItemInArray(item);
		} else {
			return IsCollectionContaining.hasItem(item);
		}
	}

	public E hasItems(boolean[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(byte[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(char[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(short[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(int[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(long[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(float[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E hasItems(double[] values) {
		return this.hasItems(ArrayHelper.toArray(values));
	}

	public E matchAllItems(String regex, String... regexs) {
		ListEveryItemMatcher matcher1 = new ListEveryItemMatcher(new Matches(regex), ItemMatcherType.AND);
		if (regexs == null || regexs.length == 0) {
			return this.assertThat(matcher1);
		}
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(matcher1);
		for (String temp : regexs) {
			ListEveryItemMatcher matcher2 = new ListEveryItemMatcher(new Matches(temp), ItemMatcherType.AND);
			list.add(matcher2);
		}
		Matcher matcher = AllOf.allOf(list);
		return this.assertThat(matcher);
	}

	public E matchAnyItems(String regex, String... regexs) {
		ListEveryItemMatcher matcher1 = new ListEveryItemMatcher(new Matches(regex), ItemMatcherType.OR);
		if (regexs == null || regexs.length == 0) {
			return this.assertThat(matcher1);
		}
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(matcher1);
		for (String temp : regexs) {
			ListEveryItemMatcher matcher2 = new ListEveryItemMatcher(new Matches(temp), ItemMatcherType.OR);
			list.add(matcher2);
		}
		Matcher matcher = AllOf.allOf(list);
		return this.assertThat(matcher);
	}

	public E matchAllItems(Matcher matcher, Matcher... matchers) {
		ListEveryItemMatcher m1 = new ListEveryItemMatcher(matcher, ItemMatcherType.AND);
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(m1);
		for (Matcher m : matchers) {
			ListEveryItemMatcher m2 = new ListEveryItemMatcher(m, ItemMatcherType.AND);
			list.add(m2);
		}
		Matcher _matcher = AllOf.allOf(list);
		return this.assertThat(_matcher);
	}

	public E matchAnyItems(Matcher matcher, Matcher... matchers) {
		ListEveryItemMatcher m1 = new ListEveryItemMatcher(matcher, ItemMatcherType.OR);
		List<Matcher> list = new ArrayList<Matcher>();
		list.add(m1);
		for (Matcher m : matchers) {
			ListEveryItemMatcher m2 = new ListEveryItemMatcher(m, ItemMatcherType.OR);
			list.add(m2);
		}
		Matcher _matcher = AllOf.allOf(list);
		return this.assertThat(_matcher);
	}
}
