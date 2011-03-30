package org.jtester.fit.spring;

import org.jtester.module.spring.SpringBeanRegister;
import org.jtester.module.spring.JTesterClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import fit.Fixture;

/**
 * 
 * @author darui.wudr
 * 
 */
public class FixtureSpringApplicationContext extends JTesterClassPathXmlApplicationContext {
	final Class<? extends Fixture> fixtureClazz;

	public FixtureSpringApplicationContext(Object testedObject, String[] configLocations,
			final Class<? extends Fixture> fixtureClazz, boolean ignoreNoSuchBean) throws BeansException {
		super(testedObject, configLocations, false, null, ignoreNoSuchBean);
		this.fixtureClazz = fixtureClazz;
		refresh();
	}

	@Override
	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) super.obtainFreshBeanFactory();

		// Fixture暂不能mock任何东西
		// MockBeanRegister.registerAllMockBean(fixtureClazz);

		SpringBeanRegister.dynamicRegisterBeanDefinition(beanFactory, fixtureClazz);
		// 增加httpInovoke的配置
		RemoteInvokerRegister.registerSpringBeanRemoteOnClient(beanFactory, fixtureClazz);

		return beanFactory;
	}
}
