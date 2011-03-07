package org.jtester.utility;

import org.jtester.fortest.beans.Manager;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class XstreamUtilTest extends JTester {
	@Test
	public void toXstreamJson() {
		String filename = "classpath:org/jtester/utility/manager.xml";
		Manager manager = SerializeUtil.fromXML(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).yearIs(2009).monthIs("08").hourIs(16);

		String json = XstreamUtil.toXstreamJson(manager);
		JTesterLogger.info(json);
	}

	@Test
	public void testFromXStreamJson() {
		String json = "{\"product\":{\"name\": \"Banana\",\"id\": 123,\"price\": 23.0}}";
		Product product = XstreamUtil.fromXStreamJson("product", Product.class, json);
		want.object(product).propertyEq("name", "Banana").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson("product", Product.class, json);
		want.object(product).propertyEq("name", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName() {
		String json = "{Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("name", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("name", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_ClazzName() {
		String json = "{org.jtester.utility.XstreamUtilTest$Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("name", "Banana dd").propertyEq("id", 123);
	}

	public static class Product {
		String name;
		int id;
		double price;
	}
}
