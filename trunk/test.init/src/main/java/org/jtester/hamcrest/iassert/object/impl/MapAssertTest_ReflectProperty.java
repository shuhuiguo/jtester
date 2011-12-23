package org.jtester.hamcrest.iassert.object.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.testng.JTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
public class MapAssertTest_ReflectProperty extends JTester {
	Map<String, String> maps = null;
	List<Map<String, String>> list = null;

	@BeforeMethod
	public void initData() {
		maps = mockMap();

		list = new ArrayList<Map<String, String>>();
		list.add(mockMap());
		Map<String, String> map2 = mockMap();
		map2.put("one", "ddd");
		list.add(map2);
	}

	public void testMapReflet() {
		Map<String, String> maps = mockMap();

		want.map(maps).propertyEq("one", "my first value");
		want.map(maps).propertyEq("three", "my third value");
	}

	public void testMapReflet_matcher() {
		Map<String, String> maps = mockMap();
		want.map(maps).propertyMatch("one", the.string().isEqualTo("my first value"));
	}

	public void testMapRefletList() {
		want.collection(list).propertyEq("one", new String[] { "my first value", "ddd" });
	}

	public void testMapRefletList_PropertyCollection() {
		want.collection(list).propertyMatch("one",
				the.collection().reflectionEq(new String[] { "ddd", "my first value" }, EqMode.IGNORE_ORDER));
	}

	public void testMapRefletList_PropertyCollection_order() {
		try {
			want.collection(list).propertyMatch("one",
					the.collection().reflectionEq(new String[] { "ddd", "my first value" }));
			want.fail();
		} catch (Throwable e) {
			String msg = e.getMessage();
			want.string(msg).contains("Expected: [\"ddd\", \"my first value\"], actual: [\"my first value\", \"ddd\"]");
		}
	}

	public void testMapRefletList_lenientOrder() {
		want.collection(list).propertyMatch("one",
				the.collection().reflectionEq(new String[] { "my first value", "ddd" }));
	}

	public void propertyCollectionRefEq() {
		String[][] expecteds = new String[][] { { "my first value", "my third value" }, { "ddd", "my third value" } };
		want.collection(list).propertyEq(new String[] { "one", "three" }, expecteds, EqMode.IGNORE_ORDER);
	}

	Map<String, String> mockMap() {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("one", "my first value");
		maps.put("two", "my second value");
		maps.put("three", "my third value");

		return maps;
	}
}
