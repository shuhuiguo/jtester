package org.jtester.json.decoder.array;

import org.jtester.json.JSON;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;


@Test(groups = { "jtester", "json" })
public class IntArrayDecoderTest extends JTester {

	@Test
	public void testParseFromJSONArray() {
		String json = "['1','2','3']";
		int[] ints = JSON.toObject(json, int[].class);
		want.array(ints).sizeEq(3).reflectionEq(new int[] { 1, 2, 3 });
	}

}
