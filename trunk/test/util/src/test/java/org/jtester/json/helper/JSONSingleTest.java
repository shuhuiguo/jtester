package org.jtester.json.helper;

import org.jtester.IAssertion;
import org.junit.Test;

public class JSONSingleTest implements IAssertion {

	@Test
	public void testToClazzName() {
		JSONSingle bean = new JSONSingle();
		bean.setValue("com.jtester.User@12345");
		String clazzname = bean.toClazzName();
		want.string(clazzname).isEqualTo("com.jtester.User");
	}

	@Test
	public void testToClazzName_NoRef() {
		JSONSingle bean = new JSONSingle();
		bean.setValue("com.jtester.User");
		String clazzname = bean.toClazzName();
		want.string(clazzname).isEqualTo("com.jtester.User");
	}

	@Test
	public void testToReferenceID() {
		JSONSingle bean = new JSONSingle();
		bean.setValue("com.jtester.User@12345");
		String clazzname = bean.toReferenceID();
		want.string(clazzname).isEqualTo("@12345");
	}

	@Test
	public void testToReferenceID_NoRef() {
		JSONSingle bean = new JSONSingle();
		bean.setValue("com.jtester.User");
		String clazzname = bean.toReferenceID();
		want.string(clazzname).isNull();
	}
}
