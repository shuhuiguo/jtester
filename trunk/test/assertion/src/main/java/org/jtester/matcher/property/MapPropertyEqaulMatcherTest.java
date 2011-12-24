package org.jtester.matcher.property;

import java.util.HashMap;

import org.jtester.beans.DataMap;
import org.jtester.json.encoder.beans.test.GenicBean;
import org.jtester.json.encoder.beans.test.User;
import org.jtester.matcher.property.reflection.EqMode;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "assertion" })
@SuppressWarnings({ "rawtypes", "serial" })
public class MapPropertyEqaulMatcherTest extends JTester {

	@Test
	public void testMatches() {
		User user = User.newInstance(123, "darui.wu");
		want.object(user).reflectionEqMap(new DataMap() {
			{
				this.put("name", "darui.wu");
			}
		});
	}

	public void testMatches_IgnoreDefault() {
		User user = User.newInstance(123, "darui.wu");
		want.object(user).reflectionEqMap(new DataMap() {
			{
				this.put("name", "darui.wu");
				this.put("id", null);
			}
		}, EqMode.IGNORE_DEFAULTS);
	}

	@Test
	public void testMatches_PropertyList() {
		GenicBean bean = GenicBean.newInstance("bean1", new String[] { "value1", "value2" });
		want.object(bean).reflectionEqMap(new DataMap() {
			{
				// this.put("name", "bean1");
				this.put("refObject", new String[] { "value1", "value2" });
			}
		});
	}

	public void testMatches_PropertyList_IgnoreDefault() {
		GenicBean bean = GenicBean.newInstance("bean1", new String[] { "value1", "value2" });
		want.object(bean).reflectionEqMap(new DataMap() {
			{
				// this.put("name", "bean1");
				this.put("refObject", new String[] { null, "value1" });
			}
		}, EqMode.IGNORE_DEFAULTS, EqMode.IGNORE_ORDER);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_PropertyList_Failure1() {
		GenicBean bean = GenicBean.newInstance("bean1", new String[] { "value1", "value2" });
		want.object(bean).reflectionEqMap(new DataMap() {
			{
				// this.put("name", "bean1");
				this.put("refObject", new String[] { null, "value1" });
			}
		}, EqMode.IGNORE_ORDER);
	}

	@Test(expectedExceptions = AssertionError.class)
	public void testMatches_PropertyList_Failure2() {
		GenicBean bean = GenicBean.newInstance("bean1", new String[] { "value1", "value2" });
		want.object(bean).reflectionEqMap(new DataMap() {
			{
				// this.put("name", "bean1");
				this.put("refObject", new String[] { null, "value1" });
			}
		}, EqMode.IGNORE_DEFAULTS);
	}

	public void testMatches_List_PoJo() {
		want.list(new User[] { User.newInstance(123, "darui.wu"), User.newInstance(124, "darui.wu") }).reflectionEqMap(
				2, new DataMap() {
					{
						this.put("name", "darui.wu");
						this.put("id", 123, 124);
					}
				}, EqMode.IGNORE_ORDER);
	}

	@SuppressWarnings("unchecked")
	public void testMatches_List_Map() {
		want.list(toList(new HashMap() {
			{
				this.put("id", 123);
				this.put("name", "darui.wu");
			}
		}, new HashMap() {
			{
				this.put("id", 124);
				this.put("name", "darui.wu");
			}
		})).reflectionEqMap(2, new DataMap() {
			{
				this.put("name", "darui.wu");
				this.put("id", 123, 124);
			}
		}, EqMode.IGNORE_ORDER);
	}
}
