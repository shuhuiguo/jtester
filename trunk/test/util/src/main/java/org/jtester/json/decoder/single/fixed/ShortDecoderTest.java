package org.jtester.json.decoder.single.fixed;

import java.util.HashMap;

import org.jtester.IAssertion;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.junit.Test;

public class ShortDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		JSONMap json = new JSONMap() {
			private static final long serialVersionUID = 1L;

			{
				this.putJSON(JSONFeature.ValueFlag, Short.valueOf("23"));
			}
		};
		ShortDecoder decoder = ShortDecoder.instance;
		Short sht = decoder.decode(json, new HashMap<String, Object>());
		want.number(sht).isEqualTo(23);
	}
}
