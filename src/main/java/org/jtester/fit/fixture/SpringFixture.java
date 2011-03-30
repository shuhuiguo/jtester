package org.jtester.fit.fixture;

import org.jtester.core.IJTester;
import org.jtester.fit.JTesterFixture;
import org.jtester.fit.spring.FixtureBeanInjector;
import org.jtester.fit.spring.RemoteInvokerRegister;
import org.jtester.fit.spring.FixtureSpringApplicationContext;
import org.jtester.utility.AnnotationUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.unitils.spring.annotation.SpringApplicationContext;

public class SpringFixture extends JTesterFixture implements IJTester {
	private ClassPathXmlApplicationContext ctx;

	/**
	 * 注入spring bean
	 */
	public SpringFixture() {
		SpringApplicationContext anotations = AnnotationUtils.getClassLevelAnnotation(SpringApplicationContext.class,
				this.getClass());
		if (anotations == null) {
			return;
		}
		try {
			String[] locations = anotations.value();
			boolean ignoreNoSuchBean = anotations.ignoreNoSuchBean();
			ctx = new FixtureSpringApplicationContext(this, locations, this.getClass(), ignoreNoSuchBean);
			FixtureBeanInjector.injectBeans(ctx, this);
			RemoteInvokerRegister.injectSpringBeanRemote(ctx, this);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("spring inject error", e);
		}
	}
}
