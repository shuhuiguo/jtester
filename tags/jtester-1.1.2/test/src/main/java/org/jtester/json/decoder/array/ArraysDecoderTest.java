package org.jtester.json.decoder.array;

import org.jtester.json.JSON;
import org.jtester.json.encoder.beans.test.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "json" })
public class ArraysDecoderTest extends JTester {

	@Test
	public void testParseFromJSONArray() {
		String json = "[{name:'name1'},{name:'name2'}]";

		User[] users = JSON.toObject(json, User[].class);
		want.array(users).sizeEq(2).propertyEq("name", new String[] { "name1", "name2" });
	}

	public void testIntsArray() {
		String json = "[{'#class':'Integer','#value':1},{'#class':'Integer','#value':2},{'#class':'Integer','#value':3},{'#class':'Integer','#value':4}]";
		int[] ints = JSON.toObject(json, int[].class);
		want.array(ints).reflectionEq(new int[] { 1, 2, 3, 4 });
	}

	public void testIntsArray2() {
		String json = "{'#class':'int[]','#value':[{'#class':'Integer','#value':1},{'#class':'Integer','#value':2},{'#class':'Integer','#value':3},{'#class':'Integer','#value':4}]}";
		int[] ints = JSON.toObject(json);
		want.array(ints).reflectionEq(new int[] { 1, 2, 3, 4 });
	}
}
