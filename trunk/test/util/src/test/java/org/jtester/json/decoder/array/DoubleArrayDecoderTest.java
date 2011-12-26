package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class DoubleArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "[12,'45.6D',\"78.9d\"]";
		double[] ints = JSON.toObject(json, double[].class);
		want.array(ints).sizeEq(3).reflectionEq(new double[] { 12d, 45.6d, 78.9d });
	}
}
