package org.jtester.matcher.property;

import static org.jtester.helper.ArrayHelper.toArray;
import static org.jtester.helper.ListHelper.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.beans.DataIterator;
import org.jtester.beans.GenicBean;
import org.jtester.beans.User;
import org.jtester.helper.ListHelper;
import org.jtester.junit.DataFrom;
import org.jtester.matcher.property.reflection.EqMode;
import org.junit.Test;

import ext.jtester.hamcrest.MatcherAssert;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class PropertiesEqualMatcherTest implements IAssertion {
	@Test
	public void testProperEqual_ComplexAndIgnoreAll() {
		GenicBean[] actuals = new GenicBean[] { GenicBean.newInstance("bean1", newUser("darui.wu")),// <br>
				GenicBean.newInstance("bean2", newMap("map2")) // <br>
		};
		List expected = new ArrayList() {
			{
				add(GenicBean.newInstance("bean2", null));
				add(toList("bean1", "darui.wu"));
			}
		};
		PropertiesEqualMatcher matcher = new PropertiesEqualMatcher(expected,
				new String[] { "name", "refObject.name" }, new EqMode[] { EqMode.IGNORE_ORDER, EqMode.IGNORE_DEFAULTS });
		MatcherAssert.assertThat(actuals, matcher);
	}

	@Test
	public void testProperEqual_ComplexAndIgnoreAll2() {
		GenicBean[] actuals = new GenicBean[] { GenicBean.newInstance("bean1", toList("list1", "list2")),
				GenicBean.newInstance("bean2", ListHelper.toList("list3", "list4")),
				GenicBean.newInstance("bean3", newUser("darui.wu")),
				GenicBean.newInstance("bean4", ListHelper.toList("list5", "list6")) };
		List expected = new ArrayList() {
			{
				add(ListHelper.toList("bean3", newUser("darui.wu")));
				add(GenicBean.newInstance("bean2", new String[] { "list4", "list3" }));
				add(GenicBean.newInstance("bean1", new String[] { "list1", null }));
				add(GenicBean.newInstance("bean4", new String[] { null, "list6" }));
			}
		};
		PropertiesEqualMatcher matcher = new PropertiesEqualMatcher(expected, new String[] { "name", "refObject" },
				new EqMode[] { EqMode.IGNORE_ORDER, EqMode.IGNORE_DEFAULTS });
		MatcherAssert.assertThat(actuals, matcher);
	}

	@Test
	@DataFrom("matchData")
	public void testProperEqual(Object actual, Object expected, String properties, EqMode[] modes, boolean match) {
		String[] props = properties.split(",");
		PropertiesEqualMatcher matcher = new PropertiesEqualMatcher(expected, props, modes);
		try {
			MatcherAssert.assertThat(actual, matcher);
			want.bool(match).isEqualTo(true);
		} catch (AssertionError error) {
			// error.printStackTrace();
			want.bool(match).isEqualTo(false);
		}
	}

	public static Iterator matchData() {
		return new DataIterator() {
			{
				data(newUser("abc"), "abc", "name", null, true);
				data(newUser("abc"), null, "name", null, false);
				data(newUser("abc"), null, "name", new EqMode[] { EqMode.IGNORE_DEFAULTS }, true);
				data(newUser("abc"), newUser("abc"), "name", null, true);
				data(newUser("abc"), ListHelper.toList(123, "abc"), "id,name", null, true);
				data(newUser("abc"), ListHelper.toList("abc", 123), "id,name", null, false);
				data(newUser("abc"), ListHelper.toList("abc", 123), "id,name", new EqMode[] { EqMode.IGNORE_ORDER },
						true);
				data(newUser("abc"), newMap("abc"), "id,name", null, true);
				data(ListHelper.toList(newUser("abc"), newUser("darui.wu")), toArray("abc", "darui.wu"), "name", null,
						true);
				data(toArray(newMap("abc"), newUser("darui.wu")),
						ListHelper.toList(newUser("abc"), newMap("darui.wu")), "name", null, true);
				data(toArray(newMap("abc"), newUser("darui.wu")), ListHelper.toList(newUser("abc"), null), "name",
						new EqMode[] { EqMode.IGNORE_DEFAULTS }, true);
				data(toArray(newMap("abc"), newUser("darui.wu")),
						ListHelper.toList(newUser("darui.wu"), newMap("abc")), "name",
						new EqMode[] { EqMode.IGNORE_ORDER }, true);
				data(toArray(newMap("abc"), newUser("darui.wu")),
						ListHelper.toList(newUser("abc"), newMap("darui.wu")), "id,name", null, true);
				data(toArray(newMap("abc"), newUser("darui.wu")), ListHelper.toList(newUser("abc"), newMap(null)),
						"id,name", new EqMode[] { EqMode.IGNORE_DEFAULTS }, true);
				data(toArray(newMap("abc"), newUser("darui.wu")), ListHelper.toList(newMap(null), newUser("abc")),
						"id,name", new EqMode[] { EqMode.IGNORE_DEFAULTS }, false);
				data(toArray(newMap("abc"), newUser("darui.wu")), ListHelper.toList(newMap(null), newUser("abc")),
						"id,name", new EqMode[] { EqMode.IGNORE_DEFAULTS, EqMode.IGNORE_ORDER }, false);
			}
		};
	}

	private static User newUser(String name) {
		return User.newInstance(123, name);
	}

	private static Map newMap(final String name) {
		return new HashMap() {
			{
				put("id", 123);
				put("name", name);
			}
		};
	}
}
