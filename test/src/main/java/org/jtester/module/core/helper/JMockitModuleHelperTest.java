package org.jtester.module.core.helper;

import java.lang.reflect.Field;

import mockit.NonStrict;

import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "spring" })
public class JMockitModuleHelperTest extends JTester {

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
