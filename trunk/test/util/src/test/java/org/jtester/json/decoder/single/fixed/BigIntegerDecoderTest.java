package org.jtester.json.decoder.single.fixed;

import java.math.BigInteger;
import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.junit.Test;

public class BigIntegerDecoderTest implements IAssertion {

	@SuppressWarnings("serial")
	@Test
	public void testDecodeSimpleValue() {
		JSONMap json = new JSONMap() {
			{
				this.putJSON(JSONFeature.ValueFlag, "1213435");
			}
		};
		BigIntegerDecoder decoder = BigIntegerDecoder.instance;
		BigInteger bigInt = decoder.decode(json, new HashMap<String, Object>());
		want.number(bigInt).isEqualTo(new BigInteger("1213435"));
	}

	@Test
	public void testSimpleValue() {
		BigInteger bigInt = JSON.toObject("1213435", BigInteger.class);
		want.number(bigInt).isEqualTo(new BigInteger("1213435"));
	}
}
