package org.jtester.json.encoder;

import org.jtester.beans.User;
import org.jtester.json.JSON;
import org.jtester.json.encoder.object.PoJoEncoder;
import org.jtester.json.helper.JSONFeature;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PropertyEncoderTest extends EncoderTest {
	User user = new User() {
		{
			this.setId(123);
			this.setName("darui.wu");
			this.setAge(45);
			this.setSalary(234.56d);
		}
	};

	@Test
	public void testEncodeValue() {

		JSONEncoder encoder = new PoJoEncoder(User.class);
		encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.IgnoreExplicitFieldType);

		encoder.encode(user, writer, references);
		String json = writer.toString();
		want.string(json).start("{#class:'org.jtester.beans.User@").contains("id:123").contains("name:'darui.wu'")
				.contains("age:45").contains("salary:234.56").contains("isFemale:false");
	}

	@Test
	public void testDecodeValue() {
		String json = "{#class:'org.jtester.beans.User@12452e8'," + // <br>
				"id:{#class:'Integer',#value:124},name:{#class:'string',#value:'my name'}," + // <br>
				"first:null,last:null,age:{#class:'Integer',#value:0},salary:{#class:'Double',#value:0}," + // <br>
				"isFemale:{#class:'Boolean',#value:false},address:null,addresses:null,phones:null,assistor:null}";
		User result = JSON.toObject(json);
		want.object(user).reflectionEq(result);
	}
}
