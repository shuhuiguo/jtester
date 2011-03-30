package org.jtester.module.jmockit;

import mockit.Mocked;

import org.jtester.fortest.beans.Manager;
import org.jtester.testng.JTester;
import org.jtester.utility.SerializeUtil;
import org.testng.annotations.Test;

@Test(groups = "jtester")
public class ExpectationsResultTest_ReturnFromXml extends JTester {
	@Mocked
	private ManagerService service;

	public void returnValue() {
		new Expectations() {
			{
				when(service.getManager(the.string().any().wanted())).thenReturnFrom(Manager.class,
						SerializeUtil.class, "manager.xml");
			}
		};
		Manager manager = service.getManager("darui.wu");
		want.object(manager).propertyEq("name", "Tony Tester");
	}

	public static class ManagerService {
		public Manager getManager(String name) {
			return null;
		}
	}
}
