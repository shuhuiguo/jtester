package org.jtester.json.decoder.single.fixed;

import java.io.File;
import java.net.URI;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class URIDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		String json = "{'#class':'URI','#value':'file:/d:/path/1.txt'}";
		URI uri = JSON.toObject(json);
		want.object(uri).isEqualTo(new File("d:/path/1.txt").toURI());
	}
}
