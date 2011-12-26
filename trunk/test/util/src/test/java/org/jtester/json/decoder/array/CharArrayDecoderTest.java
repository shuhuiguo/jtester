package org.jtester.json.decoder.array;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class CharArrayDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "['a',b,\"c\"]";
		char[] ints = JSON.toObject(json, char[].class);
		want.array(ints).sizeEq(3).reflectionEq(new char[] { 'a', 'b', 'c' });
	}
}
