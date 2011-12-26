package org.jtester.helper;

import java.util.Iterator;

import org.jtester.IAssertion;
import org.jtester.beans.DataIterator;
import org.jtester.junit.DataFrom;
import org.junit.Test;

@SuppressWarnings("rawtypes")
public class PrimitiveHelperTest2 implements IAssertion {

	@Test
	@DataFrom("testDoesEqualData")
	public void testDoesEqual(Number num1, Number num2, boolean result) {
		boolean actual = PrimitiveHelper.doesEqual(num1, num2);
		want.bool(actual).is(result);
	}

	public static Iterator testDoesEqualData() {
		return new DataIterator() {
			{
				data(1, 1L, true);
				data(Integer.valueOf(2), 2L, true);
				data(Long.valueOf(3), Short.valueOf("3"), true);
				data(4, 4.0, false);
				data(5.0d, 5.0f, true);
			}
		};
	}
}
