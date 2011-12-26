package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class LongArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['12',124L,\"456l\"]";
		long[] ints = JSON.toObject(json, long[].class);
		want.array(ints).sizeEq(3).reflectionEq(new long[] { 12L, 124L, 456L });
	}
}
