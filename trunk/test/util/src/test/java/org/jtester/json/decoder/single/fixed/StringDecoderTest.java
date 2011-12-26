package org.jtester.json.decoder.single.fixed;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class StringDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		String str = JSON.toObject("test string", String.class);
		want.string(str).isEqualTo("test string");
	}
}
