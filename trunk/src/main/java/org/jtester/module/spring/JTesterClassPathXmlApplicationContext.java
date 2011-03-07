package org.jtester.module.spring;

import org.jtester.annotations.Tracer;
import org.jtester.module.utils.TracerModuleHelper;
import org.jtester.utility.AnnotationUtils;
import org.jtester.utility.JTesterLogger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * {@link ClassPathXmlApplicationContext}的子类，运行使用@MockBean来替代spring中加载的bean值
 */
public class JTesterClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
	final Object testedObject;

	private final boolean ignoreNoSuchBean;

	public JTesterClassPathXmlApplicationContext(Object testedObject, String[] configLocations, boolean refresh,
			ApplicationContext parent, boolean ignoreNoSuchBean) throws BeansException {
		super(configLocations, false, parent);
		this.ignoreNoSuchBean = ignoreNoSuchBean;
		this.testedObject = testedObject;
		if (refresh) {
			refresh();
		}
	}

	public JTesterClassPathXmlApplicationContext(Object testedObject, String[] configLocations, boolean ignoreNoSuchBean)
			throws BeansException {
		super(configLocations, false, null);
		this.ignoreNoSuchBean = ignoreNoSuchBean;
		this.testedObject = testedObject;
	}

	@Override
	protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) super.obtainFreshBeanFactory();
		Class<?> testedClazz = this.testedObject.getClass();
		// 注册SpringBeanFor的proxy bean
		SpringBeanForFactory.registerSpringBeanForField(beanFactory, testedClazz);

		JTesterLogger.info("Refresh spring classpath application context, tested class:" + testedClazz.getName());
		SpringBeanRegister.dynamicRegisterBeanDefinition(beanFactory, testedClazz);
		boolean traceSpringBean = TracerModuleHelper.traceSpringBean();
		Tracer tracer = AnnotationUtils.getClassLevelAnnotation(Tracer.class, testedClazz);
		if ((tracer == null && traceSpringBean) || (tracer != null && tracer.spring())) {
			TracerModuleHelper.addTracerBeanDefinition(beanFactory);
		}
		return beanFactory;
	}

	@Override
	protected DefaultListableBeanFactory createBeanFactory() {
		BeanFactory parent = getInternalParentBeanFactory();
		return new JTesterBeanFactory(parent, testedObject, ignoreNoSuchBean);
	}

	/**
	 * 下面这段本来想将spring初始化时所有的bean都置成lazy-init的模式<br>
	 * 但实现中碰到问题,主要是tracer的aop初始化上出错。
	 * 
	 * @Override protected void initBeanDefinitionReader(XmlBeanDefinitionReader
	 *           beanDefinitionReader) {
	 *           beanDefinitionReader.setEventListener(new
	 *           JTesterReaderEventListener()); }
	 * @Override protected void customizeBeanFactory(DefaultListableBeanFactory
	 *           beanFactory) { super.customizeBeanFactory(beanFactory);
	 *           beanFactory.setAllowEagerClassLoading(false); }
	 * 
	 * 
	 *           自定义spring ReaderEventListener<br>
	 *           参见{@link DefaultBeanDefinitionDocumentReader} 和
	 *           {@link BeanDefinitionParserDelegate}的initDefaults方法
	 * 
	 *           <pre>
	 * 复写defaultsRegistered方法，在跑单元测试中，强制设置default-lazy-init=true属性
	 * </pre>
	 * 
	 *           public static class JTesterReaderEventListener extends
	 *           EmptyReaderEventListener {
	 * @Override public void defaultsRegistered(DefaultsDefinition
	 *           defaultsDefinition) { if (defaultsDefinition instanceof
	 *           DocumentDefaultsDefinition) { DocumentDefaultsDefinition
	 *           docDefault = (DocumentDefaultsDefinition) defaultsDefinition;
	 *           docDefault.setLazyInit("true"); } } }
	 **/
}