package org.jtester.json.decoder.array;

import java.util.ArrayList;
import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.json.JSON;
import org.junit.Test;

@SuppressWarnings({ "rawtypes" })
public class CollectionDecoderTest implements IAssertion {

	@Test
	public void testParseFromJSONArray() {
		String json = "[value1,value2,value3]";
		List list = JSON.toObject(json, ArrayList.class);
		want.collection(list).sizeEq(3).hasAllItems("value1", "value2", "value3");
	}

	public void testDecode_RefValue() {
		String json = "[{#class:org.jtester.json.encoder.beans.test.User@12c8fa8,id:12,name:'darui.wu',age:0,salary:0,isFemale:false},{#refer:@12c8fa8}]";
		List<User> list = JSON.toObject(json, ArrayList.class);
		want.collection(list).sizeEq(2);
		User u1 = list.get(0);
		User u2 = list.get(1);
		want.object(u1).same(u2);
	}
}
