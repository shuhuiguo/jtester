package org.jtester.json.encoder.array;

import java.io.StringWriter;
import java.util.ArrayList;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.json.encoder.JSONEncoder;
import org.jtester.json.helper.JSONFeature;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ObjectArrayEncoderTest implements IAssertion {
	@Test
	public void testEncode() throws Exception {
		User[] users = new User[] { User.newInstance(12, "darui.wu"), null };

		JSONEncoder encoder = JSONEncoder.get(users.getClass());
		want.object(encoder).clazIs(ObjectArrayEncoder.class);

		encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);
		StringWriter writer = new StringWriter();
		encoder.encode(users, writer, new ArrayList<String>());

		String json = writer.toString();
		want.string(json).eqIgnoreSpace("[{id:12,name:'darui.wu',age:0,salary:0,isFemale:false},null]");
	}
}
