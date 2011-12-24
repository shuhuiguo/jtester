package org.jtester.fit.fixture;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.core.context.IJTester;
import org.jtester.database.DbFitContext;
import org.jtester.database.DbFitContext.RunIn;
import org.jtester.fit.JTesterFixture;
import org.jtester.fit.spring.FixtureBeanInjector;
import org.jtester.fit.spring.RemoteInvokerRegister;
import org.jtester.fit.spring.FixtureSpringApplicationContext;
import org.jtester.helper.AnnotationHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringFixture extends JTesterFixture implements IJTester {
	private ClassPathXmlApplicationContext ctx;

	/**
	 * 注入spring bean
	 */
	public SpringFixture() {
		DbFitContext.setRunIn(RunIn.FitNesse);

		SpringApplicationContext anotations = AnnotationHelper.getClassLevelAnnotation(SpringApplicationContext.class,
				this.getClass());
		if (anotations == null) {
			return;
		}
		try {
			String[] locations = anotations.value();
			boolean ignoreNoSuchBean = anotations.ignoreNoSuchBean();
			ctx = new FixtureSpringApplicationContext(locations, this.getClass(), ignoreNoSuchBean);
			FixtureBeanInjector.injectBeans(ctx, this);
			RemoteInvokerRegister.injectSpringBeanRemote(ctx, this);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("spring inject error", e);
		}
	}
}
