package org.jtester.json.decoder;

import java.util.Map;

import org.jtester.json.JSON;
import org.jtester.json.encoder.beans.test.User;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "json" })
public class ArrayDecoderTest extends JTester {

	public void testDecode_Reference() {
		String json = "[{#class:org.jtester.json.encoder.beans.test.User@19762f,id:{#class:Integer,'#value':1},name:{#class:string,'#value':'darui.wu'},age:{#class:Integer,'#value':0},salary:{#class:Double,'#value':0},isFemale:{#class:Boolean,'#value':false}},{#refer:@19762f}]";
		User[] users = JSON.toObject(json, User[].class);
		want.array(users).sizeEq(2);
		want.object(users[0]).same(users[1]);
	}

	public void testDecode() {
		String json = "{#class:org.jtester.json.encoder.beans.test.User@19762f,id:{#class:Integer,'#value':1},name:{#class:string,'#value':'darui.wu'},age:{#class:Integer,'#value':0},salary:{#class:Double,'#value':0},isFemale:{#class:Boolean,'#value':false}}";

		User[] users = JSON.toObject("[" + json + "," + json + "]", User[].class);
		want.array(users).sizeEq(2);
		want.object(users[0]).reflectionEq(users[1]);
	}

	public void testDecode_ArrayRef() {
		String json = "{'key1':{#class:'[I@123',#value:[1,2,3]},'key2':{#refer:@123}}";
		Map<String, int[]> map = JSON.toObject(json);
		want.map(map).sizeEq(2).hasKeys("key1", "key2");
		int[] ia1 = map.get("key1");
		int[] ia2 = map.get("key2");
		want.object(ia1).reflectionEq(new int[] { 1, 2, 3 }).same(ia2);
	}
}
