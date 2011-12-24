package org.jtester.json.decoder.single.fixed;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class ByteDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		Byte b = JSON.toObject("1", Byte.class);
		want.bite(b).isEqualTo(Byte.valueOf("1"));
	}
}
