package org.jtester.module.jmock;

import org.jtester.annotations.deprecated.Mock;
import org.jtester.core.Je;
import org.jtester.fortest.beans.Manager;
import org.jtester.module.core.helper.JmockModuleHelper;
import org.jtester.testng.JTester;
import org.jtester.utility.SerializeUtil;
import org.testng.annotations.Test;

@SuppressWarnings("deprecation")
@Test(groups = "jtester")
public class ReturnValueTest extends JTester {
	@Mock
	private ManagerService service;

	public void returnValue() {
		JmockModuleHelper.checking(new Je() {
			{
				will.call.one(service).getManager(the.string().any().wanted());
				will.returns.value(Manager.class, SerializeUtil.class, "manager.xml");
			}
		});
		Manager manager = service.getManager("darui.wu");
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	public static class ManagerService {
		public Manager getManager(String name) {
			return null;
		}
	}
}
