package org.jtester.matcher.clazz;

import mockit.Mocked;

import org.jtester.IAssertion;
import org.junit.Test;

public class ClassAssignFromMatcherTest implements IAssertion {

	public void testClassAssignFromMatcher() {
		want.object(new B()).clazIsSubFrom(A.class);
	}

	@Mocked
	A a;

	/**
	 * 测试wanted(Class)中传入的参数是null时，断言是正确的。
	 */
	@Test
	public void testClassAssignFromMatcher_wanted() {
		new Expectations() {
			{
				a.say(the.string().wanted(String.class));
				result = "mock";
			}
		};
		String result = a.say(null);
		want.string(result).isEqualTo("mock");
	}

	public static class A {
		public String say(String name) {
			return "nothing";
		}
	}

	public static class B extends A {

	}
}
