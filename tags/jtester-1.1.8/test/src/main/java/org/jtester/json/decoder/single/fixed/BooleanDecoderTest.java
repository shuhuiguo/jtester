package org.jtester.json.decoder.single.fixed;

import java.util.HashMap;

import org.jtester.json.JSON;
import org.jtester.json.decoder.single.fixed.BooleanDecoder;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.jtester.testng.JTester;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "json" })
public class BooleanDecoderTest extends JTester {

	@Test
	public void testDecodeSimpleValue() {
		JSONMap json = new JSONMap() {
			private static final long serialVersionUID = 1L;
			{
				this.putJSON(JSONFeature.ValueFlag, true);
			}
		};
		BooleanDecoder decoder = BooleanDecoder.instance;
		Boolean bl = decoder.decode(json, new HashMap<String, Object>());
		want.bool(bl).is(true);
	}

	@Test(dataProvider = "simple_value")
	public void testSimpleValue(String json, boolean expected) {
		Boolean bool = JSON.toObject(json, Boolean.class);
		want.bool(bool).is(expected);
	}

	@DataProvider
	public Object[][] simple_value() {
		return new Object[][] { { "0", false },// <br>
				{ "'1'", true }, /** <br> */
				{ "'tRue'", true }, /** <br> */
				{ "\"true\"", true }, /** <br> */
				{ "False", false }, /** <br> */
				{ "false", false } /** <br> */
		};
	}
}
