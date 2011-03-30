package org.jtester.utility;

import org.apache.log4j.Logger;
import org.jtester.fortest.beans.Manager;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class XstreamUtilTest extends JTester {
	private final static Logger log4j = Logger.getLogger(XstreamUtilTest.class);

	@Test
	public void toXstreamJson() {
		String filename = "classpath:org/jtester/utility/manager.xml";
		Manager manager = SerializeUtil.fromXML(Manager.class, filename);
		want.object(manager).propertyEq("wikiName", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).yearIs(2009).monthIs("08").hourIs(16);

		String json = XstreamUtil.toXstreamJson(manager);
		log4j.info(json);
	}

	@Test
	public void testFromXStreamJson() {
		String json = "{\"product\":{\"name\": \"Banana\",\"id\": 123,\"price\": 23.0}}";
		Product product = XstreamUtil.fromXStreamJson("product", Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson("product", Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName() {
		String json = "{Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_ClazzName() {
		String json = "{org.jtester.utility.XstreamUtilTest$Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = XstreamUtil.fromXStreamJson(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	public static class Product {
		String name;
		int id;
		double price;
	}
}
