package org.jtester.json.decoder.single.fixed;

import java.math.BigDecimal;
import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.junit.Test;

@SuppressWarnings("serial")
public class BigDecimalDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		JSONMap json = new JSONMap() {
			{
				this.putJSON(JSONFeature.ValueFlag, "1213435");
			}
		};
		BigDecimalDecoder decoder = BigDecimalDecoder.instance;
		BigDecimal decimal = decoder.decode(json, new HashMap<String, Object>());
		want.number(decimal).isEqualTo(new BigDecimal("1213435"));
	}

	@Test
	public void testSimpleValue() {
		BigDecimal decimal = JSON.toObject("1213435", BigDecimal.class);
		want.number(decimal).isEqualTo(new BigDecimal("1213435"));
	}
}
