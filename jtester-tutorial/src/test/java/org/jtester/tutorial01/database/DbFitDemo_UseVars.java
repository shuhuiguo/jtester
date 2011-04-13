package org.jtester.tutorial01.database;

import java.util.HashMap;
import java.util.Map;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.annotations.DbFit;
import org.jtester.annotations.FitVar;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.jtester.tutorial01.services.PhoneBookService;
import org.testng.annotations.Test;

@Test
@SpringApplicationContext( { "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class DbFitDemo_UseVars extends JTester {
	@SpringBeanByName
	PhoneBookService phoneBookService;

	@DbFit(when = "usageVars.when.wiki", then = "usageVars.then.wiki", vars = {
			@FitVar(key = "martin", value = "i am martin.bob"), @FitVar(key = "jobs", value = "i am jobs.wang") })
	public void usageVars() {
		phoneBookService.insertPhoneBook("darui.wu", "15922225555", "classmate");
	}

	/**
	 * 动态传入变量值
	 */
	public void usageVars_dynamic() {
		Map<String, String> symbols = new HashMap<String, String>();
		symbols.put("martin", "i am martin.bob");
		symbols.put("jobs", "i am jobs.wang");
		fit.runDbFit(DbFitDemo_UseVars.class, symbols, "usageVars.when.wiki");
		phoneBookService.insertPhoneBook("darui.wu", "15922225555", "classmate");

		symbols.put("gname", "classmate");
		fit.runDbFit(DbFitDemo_UseVars.class, symbols, "usageVars2.then.wiki");
	}
}
