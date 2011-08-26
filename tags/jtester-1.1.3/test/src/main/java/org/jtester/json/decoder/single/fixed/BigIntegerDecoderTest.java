package org.jtester.json.decoder.single.fixed;

import java.math.BigInteger;
import java.util.HashMap;

import org.jtester.json.JSON;
import org.jtester.json.decoder.single.fixed.BigIntegerDecoder;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "json" })
public class BigIntegerDecoderTest extends JTester {

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

	public void testSimpleValue() {
		BigInteger bigInt = JSON.toObject("1213435", BigInteger.class);
		want.number(bigInt).isEqualTo(new BigInteger("1213435"));
	}
}
