package org.jtester.json.decoder.single.fixed;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class LongDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		Long l = JSON.toObject("1234L", Long.class);
		want.number(l).isEqualTo(1234L);
	}

}
