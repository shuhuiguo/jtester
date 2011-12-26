package org.jtester.json.decoder.object;

import org.jtester.IAssertion;
import org.jtester.beans.Manager;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.junit.Test;

public class PoJoDecoderTest implements IAssertion {
	String json = "";

	@Test
	public void testPoJoEncoder() {
		Manager manager = Manager.mock();
		this.json = JSON.toJSON(manager, JSONFeature.UseSingleQuote);
		want.string(json).contains("Tony Tester");
	}

	/**
	 * 对象有多重继承的情况
	 */
	@Test
	// dependsOnMethods = "testPoJoEncoder"
	public void testPoJoDecoder() {
		Manager manager = JSON.toObject(json, Manager.class);
		want.object(manager).propertyEq("name", "Tony Tester");
	}
}
