package org.jtester.json.encoder;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jtester.IAssertion;
import org.jtester.json.helper.JSONFeature;
import org.junit.Before;

@SuppressWarnings("rawtypes")
public abstract class EncoderTest implements IAssertion {
	protected StringWriter writer = null;
	protected List<String> references = null;

	@Before
	public void initStringWriter() {
		writer = new StringWriter();
		this.references = new ArrayList<String>();
	}

	/**
	 * 设置编码器输出json串不带class标记
	 * 
	 * @param encoder
	 */
	protected void setUnmarkFeature(JSONEncoder encoder) {
		encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);
	}
}
