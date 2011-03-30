package org.jtester.unitils.spring;

import org.jtester.fortest.service.UserService;
import org.jtester.unitils.dbwiki.WikiDataSet;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = { "jtester", "mockbean" })
public class MockedBeanByNameTest_NoMock extends MockedBeanByNameTest_Base {
	@SpringBeanByName
	private UserService userService;

	@WikiDataSet( { "MockBeanByNameTest_NoMock.paySalary.wiki" })
	public void paySalary() {
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4000d);
	}
}
