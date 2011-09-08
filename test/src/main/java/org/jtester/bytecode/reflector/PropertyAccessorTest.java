package org.jtester.bytecode.reflector;

import java.util.HashMap;

import org.jtester.bytecode.reflector.PropertyAccessor;
import org.jtester.json.encoder.beans.test.User;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
@Test(groups = "jtester")
public class PropertyAccessorTest extends JTester {

	@Test(dataProvider = "testGetPropertyDatas")
	public void testGetProperty(Object target, String property, String expected) {
		String value = (String) PropertyAccessor.getPropertyByOgnl(target, property, true);
		want.string(value).isEqualTo(expected);
	}

	@DataProvider
	public Object[][] testGetPropertyDatas() {
		return new Object[][] { { new HashMap() {
			{
				this.put("key1", "value1");
				this.put("key2", "value2");
			}
		}, "key1", "value1" },// <br>
				{ new HashMap() {
					{
						this.put("key1", User.newInstance(12, "darui.wu"));
						this.put("key2", "value2");
					}
				}, "key1.name", "darui.wu" },// <br>
				{ User.newInstance(12, "darui.wu"), "name", "darui.wu" } // <br>
		};
	}
}
