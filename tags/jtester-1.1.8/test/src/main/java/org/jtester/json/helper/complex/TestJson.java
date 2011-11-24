package org.jtester.json.helper.complex;

import java.util.ArrayList;

import org.jtester.hamcrest.matcher.modes.ItemsMode;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.jtester.testng.JTester;
import org.jtester.utility.DateUtil;
import org.testng.annotations.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestJson extends JTester {
	@Test
	public void testList() {
		ComplexPoJo dbpojo = new ComplexPoJo();
		dbpojo.setDateValue(DateUtil.parse("2011-08-19 16:04:38"));
		ArrayList list = new ArrayList();
		list.add(dbpojo);

		String jtestJson = JSON.toJSON(list, JSONFeature.QuoteAllItems, JSONFeature.SkipNullValue);
		System.out.println(jtestJson);
		ArrayList obj = JSON.toObject(jtestJson);
		want.collection(obj).notNull()
				.propertyMatch(ItemsMode.AnyItems, "dateValue", the.date().eqByFormat("2011-08-19"));
	}
}
