package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class ByteArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['1','0',1]";
		byte[] ints = JSON.toObject(json, byte[].class);
		want.array(ints).sizeEq(3).reflectionEq(new byte[] { 1, 0, 1 });
	}
}
