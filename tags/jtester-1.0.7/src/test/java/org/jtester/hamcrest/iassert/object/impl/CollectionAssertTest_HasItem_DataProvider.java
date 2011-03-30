package org.jtester.hamcrest.iassert.object.impl;

import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "JTester" })
public class CollectionAssertTest_HasItem_DataProvider extends JTester {

	@DataProvider(name = "provide_hasitems")
	public Object[][] provideArray() {
		Object[][] result = new Object[4][2];
		result[0][0] = new Integer[] { 1, 2, 3 };
		result[0][1] = new Integer[] { 1, 2 };
		result[1][0] = new Character[] { 'a', 'b', 'c' };
		result[1][1] = new Character[] { 'a', 'b' };
		result[2][0] = new Boolean[] { true, false };
		result[2][1] = new Boolean[] { true };
		result[3][0] = new Double[] { 1.2d, 2.8d, 3.9d };
		result[3][1] = new Double[] { 1.2d, 3.9d };
		return result;
	}

	@Test(dataProvider = "provide_hasitems")
	public void hasItems(Object[] actual, Object[] expected) {
		want.array(actual).hasItems(expected);
	}
}
