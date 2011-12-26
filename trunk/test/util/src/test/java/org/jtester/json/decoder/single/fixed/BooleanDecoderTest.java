package org.jtester.json.decoder.single.fixed;

import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.jtester.junit.DataFrom;
import org.junit.Test;

public class BooleanDecoderTest implements IAssertion {

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

	@Test
	@DataFrom("simple_value")
	public void testSimpleValue(String json, boolean expected) {
		Boolean bool = JSON.toObject(json, Boolean.class);
		want.bool(bool).is(expected);
	}

	public static Object[][] simple_value() {
		return new Object[][] { { "0", false },// <br>
				{ "'1'", true }, /** <br> */
				{ "'tRue'", true }, /** <br> */
				{ "\"true\"", true }, /** <br> */
				{ "False", false }, /** <br> */
				{ "false", false } /** <br> */
		};
	}
}
