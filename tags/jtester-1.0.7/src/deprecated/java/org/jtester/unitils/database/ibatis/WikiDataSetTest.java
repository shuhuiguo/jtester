package org.jtester.unitils.database.ibatis;

import org.jtester.fortest.beans.User;
import org.jtester.fortest.service.UserService;
import org.jtester.testng.JTester;
import org.jtester.unitils.dbwiki.WikiDataSet;
import org.jtester.unitils.dbwiki.WikiExpectedDataSet;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.RefreshLoadStrategy;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@Test(groups = { "JTester" })
@SpringApplicationContext( { "classpath:org/jtester/fortest/spring/beans.xml",
		"classpath:org/jtester/fortest/spring/data-source.xml" })
public class WikiDataSetTest extends JTester {
	@SpringBeanByName
	private UserService userService;

	@DataSet( { "DbTester.paySalary.xml" })
	public void paySalary() {
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4000d);
	}

	@WikiDataSet(value = { "DbTester.paySalary.wiki" }, loadStrategy = RefreshLoadStrategy.class)
	public void paySalary_wiki() {
		double total = this.userService.paySalary("310000");
		want.number(total).isEqualTo(4000d);
	}

	@WikiDataSet(value = { "DbTester.paySalary.wiki" })
	@WikiExpectedDataSet("DbTester.insert.expected.wiki")
	public void paySalary_insert() {
		User user = new User();
		user.setFirst("first name");
		user.setPostcode("320001");
		user.setSarary(23.02d);
		this.userService.insertUser(user);
	}
}
