package org.jtester.matcher.property.comparator;

import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.beans.DataMap;
import org.jtester.beans.User;
import org.jtester.matcher.property.reflection.EqMode;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
public class MapComparatorTest implements IAssertion {
	@Test
	public void testMap() {
		want.object(new HashMap() {
			{
				this.put("id", 123);
				this.put("name", "darui.wu");
			}
		}).reflectionEq(new HashMap() {
			{
				this.put("id", 123);
				this.put("name", null);
			}
		}, EqMode.IGNORE_DEFAULTS);
	}

	@Test
	public void testMap2() {
		want.object(User.newInstance(123, "darui.wu")).reflectionEqMap(new DataMap() {
			{
				this.put("id", 123);
				this.put("name", null);
			}
		}, EqMode.IGNORE_DEFAULTS);
	}

	@Test(expected = AssertionError.class)
	public void testMap3() {
		want.object(new HashMap() {
			{
				this.put("id", 123);
				this.put("name", "darui.wu");
			}
		}).reflectionEq(new HashMap() {
			{
				this.put("id", 123);
			}
		}, EqMode.IGNORE_DEFAULTS);
	}

}
