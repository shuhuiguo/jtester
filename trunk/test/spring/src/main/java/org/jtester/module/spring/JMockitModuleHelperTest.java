package org.jtester.module.spring;

import java.lang.reflect.Field;

import mockit.NonStrict;

import org.jtester.IAssertion;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.module.helper.JMockitModuleHelper;
import org.junit.Test;

public class JMockitModuleHelperTest implements IAssertion {

	@Test
	public void testDoesSpringBeanFieldIllegal() throws Exception {
		Field field = TestedClazz.class.getDeclaredField("field");
		want.object(field).notNull();
		try {
			JMockitModuleHelper.doesSpringBeanFieldIllegal(field);
			want.fail();
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).containsInOrder("@SpringBeanByName/@SpringBeanByType", "@NonStrict");
		}
	}
}

class TestedClazz {
	@SpringBeanByName
	@NonStrict
	Object field;
}
