package org.jtester.hamcrest.matcher.property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.hamcrest.matcher.property.reflection.EqMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

import ext.jtester.hamcrest.MatcherAssert;

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
@Test
public class MapListPropertyEqaulMatcherTest extends JTester {

	public void testMapListPropertyEqaulMatcher() {

		List<Map<String, ?>> expected = new ArrayList<Map<String, ?>>() {
			{
				this.add(new HashMap() {
					{
						this.put("id", 123);
					}
				});
				this.add(new HashMap() {
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
