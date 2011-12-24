package org.jtester.json.decoder.single.spec;

import java.io.File;

import org.jtester.IAssertion;
import org.jtester.json.JSON;
import org.junit.Test;

public class FileDecoderTest implements IAssertion {

	@Test
	public void testDecodeSimpleValue() {
		String json = "['d:/1.txt','d:/2.txt']";
		File[] files = JSON.toObject(json, File[].class);
		want.array(files).sizeEq(2);
	}
}
