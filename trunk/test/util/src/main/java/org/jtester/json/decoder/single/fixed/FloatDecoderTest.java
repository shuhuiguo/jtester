package org.jtester.json.decoder.single.fixed;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class FloatDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		Float f = JSON.toObject("12.34f", Float.class);
		want.number(f).isEqualTo(12.34f);
	}
}
