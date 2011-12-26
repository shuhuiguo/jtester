package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class FloatArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['12',12.4,\"45.6f\"]";
		float[] ints = JSON.toObject(json, float[].class);
		want.array(ints).sizeEq(3).reflectionEq(new float[] { 12f, 12.4f, 45.6f });
	}
}
