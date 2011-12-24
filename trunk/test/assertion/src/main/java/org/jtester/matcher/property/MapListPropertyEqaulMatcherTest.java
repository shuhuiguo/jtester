package org.jtester.matcher.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.matcher.property.MapListPropertyEqaulMatcher;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;
import org.jtester.beans.DataMap;

import ext.jtester.hamcrest.MatcherAssert;

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
@Test
public class MapListPropertyEqaulMatcherTest extends JTester {

	public void testMapListPropertyEqaulMatcher() {

		List<org.jtester.beans.DataMap> expected = new ArrayList<org.jtester.beans.DataMap>() {
			{
				this.add(new DataMap() {
					{
						this.put("id", 123);
					}
				});
				this.add(new DataMap() {
					{
						this.put("name", "jobs.he");
					}
				});
			}
		};
		MapListPropertyEqaulMatcher matcher = new MapListPropertyEqaulMatcher(expected,
				new EqMode[] { EqMode.IGNORE_DEFAULTS });

		List<Map<String, ?>> actual = new ArrayList<Map<String, ?>>() {
			{
				this.add(new HashMap() {
					{
						this.put("id", 123);
						this.put("name", "darui.wu");
					}
				});
				this.add(new HashMap() {
					{
						this.put("id", 124);
						this.put("name", "jobs.he");
					}
				});
			}
		};
		MatcherAssert.assertThat(actual, matcher);
	}
}
