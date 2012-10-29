package org.jtester.json.decoder.single;

import java.io.File;

import org.jtester.json.JSON;
import org.jtester.junit.JTester;
import org.junit.Test;

public class FileDecoderTest extends JTester {

	@Test
	public void testDecodeSimpleValue() {
		String json = "['d:/1.txt','d:/2.txt']";
		File[] files = JSON.toObject(json, File[].class);
		want.array(files).sizeEq(2);
	}

	@Test
	public void testDecodeSimpleValue_FileArray2D() {
		String json = "[['d:/1.txt'],['d:/2.txt']]";
		File[][] files = JSON.toObject(json, File[][].class);
		want.array(files).sizeEq(2);
	}
}
