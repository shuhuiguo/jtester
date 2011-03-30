package org.jtester.module.spring.autowired;

import org.jtester.module.spring.beanfortest.Bird;
import org.jtester.module.spring.beanfortest.Cat;
import org.jtester.testng.JTester;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

public class AutoProxyTest extends JTester {
	@Test
	public void testAutoProxyTest() {
		String[] paths = { "org/jtester/spring/beanfortest/animal-aop.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
		Cat tiger = (Cat) ctx.getBean("tiger");
		tiger.hasHotBlood();
		Bird albatross = (Bird) ctx.getBean("albatross");
		albatross.hasBeak();
	}

	@Test
	public void testAutoProxyTest2() {
		String[] paths = { "org/jtester/spring/beanfortest/animal-aop2.xml" };
		ApplicationContext ctx = new ClassPathXmlApplicationContext(paths);
		Cat tiger = (Cat) ctx.getBean("tiger");
		tiger.hasHotBlood();
		Bird albatross = (Bird) ctx.getBean("albatross");
		albatross.hasBeak();
	}
}
