package org.jtester.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jtester.fortest.beans.Manager;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

public class JsonHelperTest_JsonString extends JTester {
	private final static Logger log4j = Logger.getLogger(JsonHelperTest_JsonString.class);

	@Test
	public void toXstreamJson() {
		String filename = "classpath:org/jtester/utility/manager.xml";
		Manager manager = JsonHelper.fromJsonFile(Manager.class, filename);
		want.object(manager).propertyEq("wikiName", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);

		String json = JsonHelper.toJSON(manager);
		log4j.info(json);
	}

	@Test
	public void testFromXStreamJson() {
		String json = "{\"product\":{\"name\": \"Banana\",\"id\": 123,\"price\": 23.0}}";
		Product product = JsonHelper.fromJSON("product", Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = JsonHelper.fromJSON("product", Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName() {
		String json = "{Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = JsonHelper.fromJSON(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_SimpleName2() {
		String json = "{product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = JsonHelper.fromJSON(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	@Test
	public void testFromXStreamJson_ClazzName() {
		String json = "{org.jtester.utility.XstreamUtilTest$Product:{name: Banana dd,id: 123,price: 23.0}}";
		Product product = JsonHelper.fromJSON(Product.class, json);
		want.object(product).propertyEq("wikiName", "Banana dd").propertyEq("id", 123);
	}

	public static class Product {
		String name;
		int id;
		double price;

		public Product() {
			this.name = "myname";
			this.id = 100;
			this.price = 1333.00d;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Test
	public void testJsonPojo() {
		Product p = new Product();
		String jsonString = JsonHelper.toJSON(p);
		want.string(jsonString).isEqualTo("{\"price\":1333,\"id\":100,\"name\":\"myname\"}");
	}

	@Test
	public void testJsonMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 123);
		map.put("name", "张三");

		String text = JsonHelper.toJSON(map);
		want.string(text).isEqualTo("{\"name\":\"张三\",\"id\":123}");
	}
}
