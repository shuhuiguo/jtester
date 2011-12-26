package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class BooleanArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "[1,false,'true',true]";
		boolean[] ints = JSON.toObject(json, boolean[].class);
		want.array(ints).sizeEq(4).reflectionEq(new boolean[] { true, false, true, true });
	}
}
