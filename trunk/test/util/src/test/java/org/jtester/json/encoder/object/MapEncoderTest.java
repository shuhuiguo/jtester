package org.jtester.json.encoder.object;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.json.encoder.JSONEncoder;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MapEncoderTest implements IAssertion {

	@Test
	public void testGetPropertyEncoders() throws Exception {
		Map<String, String> map = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				this.put("key1", "value1");
				this.put("key2", "value2");
			}
		};
		StringWriter writer = new StringWriter();
		JSONEncoder encoder = new MapEncoder(HashMap.class);
		encoder.encode(map, writer, new ArrayList<String>());
		String json = writer.toString();
		System.out.println("JONS: " + json);
	}
}
