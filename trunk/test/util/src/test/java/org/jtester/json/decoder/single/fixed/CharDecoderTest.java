package org.jtester.json.decoder.single.fixed;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class CharDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		Character ch = JSON.toObject("'a'", Character.class);
		want.character(ch).isEqualTo('a');
	}
}
