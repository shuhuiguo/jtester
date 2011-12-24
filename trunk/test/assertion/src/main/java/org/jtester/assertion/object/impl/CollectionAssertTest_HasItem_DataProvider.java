package org.jtester.assertion.object.impl;

import java.util.Iterator;

import org.jtester.IAssertion;
import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class CollectionAssertTest_HasItem_DataProvider implements IAssertion {

	public static Iterator provideArray() {
		return new DataIterator() {
			{
				data(new Integer[] { 1, 2, 3 }, 1, new Integer[] { 2 });
				data(new Character[] { 'a', 'b', 'c' }, 'a', new Character[] { 'b' });
				data(new Boolean[] { true, false }, true, null);
				data(new Double[] { 1.2d, 2.8d, 3.9d }, 1.2d, new Double[] { 3.9d });
			}
		};
	}

	@Test
	@DataFrom("provide_hasitems")
	public void hasItems(Object[] actual, Object firstExpected, Object[] expected) {
		want.array(actual).hasAllItems(firstExpected, expected);
	}
}
