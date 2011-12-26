package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class IntArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['1','2','3']";
		int[] ints = JSON.toObject(json, int[].class);
		want.array(ints).sizeEq(3).reflectionEq(new int[] { 1, 2, 3 });
	}

}
