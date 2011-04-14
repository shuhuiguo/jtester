package org.jtester.tutorial.spring;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.testng.annotations.Test;

@Test(description = "动态注册spring bean演示")
@SpringApplicationContext({ "spring/data-source.xml" })
@AutoBeanInject(maps = { @BeanMap(intf = "**.*", impl = "**.*Impl"),// <br>
		@BeanMap(intf = "**.*", impl = "**.impl.*Impl") })
public class SpringBeanFromDemo {

}
