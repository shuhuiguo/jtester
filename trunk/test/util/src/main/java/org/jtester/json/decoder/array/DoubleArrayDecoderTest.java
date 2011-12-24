package org.jtester.json.decoder.array;

import org.jtester.json.JSON;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;


public class DoubleArrayDecoderTest extends JTester {

	@Test(groups = { "jtester", "json" })
	public void testParseFromJSONArray() {
		String json = "[12,'45.6D',\"78.9d\"]";
		double[] ints = JSON.toObject(json, double[].class);
		want.array(ints).sizeEq(3).reflectionEq(new double[] { 12d, 45.6d, 78.9d });
	}
}
