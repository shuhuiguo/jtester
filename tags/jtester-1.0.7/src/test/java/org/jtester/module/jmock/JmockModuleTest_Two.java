package org.jtester.module.jmock;

import org.jtester.annotations.Inject;
import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.fortest.beans.ISpeak;
import org.jtester.fortest.beans.Person;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = { "JTester" })
public class JmockModuleTest_Two extends JTester {

	private Person person = new Person();
	@Mock
	@Inject(targets = "person")
	private ISpeak speak;

	public void sayHello() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.oneOf(speak).say(the.string().contains("darui.wu").wanted());
			}
		});
		person.sayHelloTo("darui.wu");
	}

	public void sayHello_2() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.oneOf(speak).say(with(the.string().contains("darui.wu")));
			}
		});
		person.sayHelloTo("darui.wu");
	}

	@Test
	public void sayHello_ThrowRuntimeException_WithoutWrapBySpring() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.oneOf(speak).say(the.string().contains("darui.wu").wanted());
				will.throwException(new RuntimeException("test"));
			}
		});
		try {
			person.sayHelloTo("darui.wu");
		} catch (Exception e) {
			String message = e.getMessage();
			want.string(message).contains("test");
		}
	}
}
