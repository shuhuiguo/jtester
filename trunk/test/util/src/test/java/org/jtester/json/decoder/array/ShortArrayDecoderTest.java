package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class ShortArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['12',124,\"456\"]";
		short[] ints = JSON.toObject(json, short[].class);
		want.array(ints).sizeEq(3).reflectionEq(new short[] { 12, 124, 456 });
	}
}
