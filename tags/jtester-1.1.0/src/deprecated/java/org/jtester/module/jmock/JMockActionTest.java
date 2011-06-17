package org.jtester.module.jmock;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = "jtester")
public class JMockActionTest extends JTester {
	private SomeThing someThing = new SomeThing();
	@Mock(injectInto = "someThing")
	private SomeInterface intf;

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(intf).addElement(the.collection().wanted(List.class));
				will(new Action() {
					public void describeTo(Description description) {
						description.appendText("adds item to a collection");

					}

					public Object invoke(Invocation invocation) throws Throwable {
						List<String> list = (List<String>) invocation.getParameter(0);
						list.add("three");
						return null;
					}
				});
			}
		});
		List<String> list = someThing.myBiz();
		want.collection(list).sizeEq(3).hasItems("three");
	}

	public static class SomeThing {
		private SomeInterface intf;

		public List<String> myBiz() {
			List<String> list = new ArrayList<String>();
			list.add("one");
			list.add("two");
			this.intf.addElement(list);
			return list;

		}

		public void setIntf(SomeInterface intf) {
			this.intf = intf;
		}
	}

	public static interface SomeInterface {
		void addElement(List<String> list);
	}
}
