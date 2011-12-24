package org.jtester.json.decoder.single.fixed;

import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONMap;
import org.jtester.junit.DataFrom;
import org.junit.Test;

public class DoubleDecoderTest implements IAssertion {
	@Test
	public void testDecodeSimpleValue() {
		JSONMap json = new JSONMap() {
			private static final long serialVersionUID = 1L;
			{
				this.putJSON("#value", 23243.34d);
			}
		};
		DoubleDecoder decoder = DoubleDecoder.instance;
		Double d = decoder.decode(json, new HashMap<String, Object>());
		want.number(d).isEqualTo(23243.34d);
	}

	@Test
	@DataFrom("simple_value")
	public void testSimpleValue(String json, double expected) {
		Double actual = JSON.toObject(json, Double.class);
		want.number(actual).isEqualTo(expected);
	}

	public static Object[][] simple_value() {
		return new Object[][] { { "34234.34d", 34234.34d },// <br>
				{ "'34234.34d'", 34234.34d }, /** <br> */
				{ "34234.34", 34234.34d } /** <br> */
		};
	}
}
