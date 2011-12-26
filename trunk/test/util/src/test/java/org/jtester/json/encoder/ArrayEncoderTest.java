package org.jtester.json.encoder;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.junit.Test;

public class ArrayEncoderTest implements IAssertion {

	@Test
	public void testEncode() {
		User[] users = new User[2];
		users[0] = User.newInstance(12, "darui.wu");
		users[1] = users[0];

		String json = JSON.toJSON(users, JSONFeature.UseSingleQuote);
		want.string(json).contains("#class:'org.jtester.beans.User@").contains("#refer:@");
	}
}