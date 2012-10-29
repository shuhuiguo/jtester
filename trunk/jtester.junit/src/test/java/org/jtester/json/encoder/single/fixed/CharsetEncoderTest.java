package org.jtester.json.encoder.single.fixed;

import java.nio.charset.Charset;

import org.jtester.json.encoder.EncoderTest;
import org.jtester.junit.annotations.DataFrom;
import org.junit.Test;

public class CharsetEncoderTest extends EncoderTest {

	@Test
	@DataFrom("charset_data")
	public void testCharsetEncoder(String name, String expected) {
		Charset cs = Charset.forName(name);

		CharsetEncoder encoder = CharsetEncoder.instance;
		this.setUnmarkFeature(encoder);
		encoder.encode(cs, writer, references);

		String json = writer.toString();
		want.string(json).isEqualTo(expected);
	}

	public static Object[][] charset_data() {
		return new Object[][] { { "utf8", "'UTF-8'" },// <br>
				{ "UTF-8", "'UTF-8'" },// <br>
				{ "gbk", "'GBK'" } // <br>
		};
	}
}
