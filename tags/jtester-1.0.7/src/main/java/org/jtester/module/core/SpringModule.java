package org.jtester.module.core;

import static org.jtester.module.utils.PropertyUtils.getInstance;
import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.jtester.core.intf.ISpringContextContainer;
import org.jtester.exception.JTesterException;
import org.jtester.module.TestListener;
import org.jtester.module.jmock.MockBeanRegister;
import org.jtester.module.spring.ApplicationContextFactory;
import org.jtester.module.utils.SpringModuleHelper;
import org.jtester.reflector.ReflectUtil;
import org.jtester.utility.JTesterLogger;
import org.jtester.utility.StringHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractApplicationContext;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;
import org.unitils.spring.annotation.SpringBeanByType;

public class SpringModule implements Module {

	/* Property key of the class name of the application context factory */
	public static final String PROPKEY_APPLICATION_CONTEXT_FACTORY_CLASS_NAME = "SpringModule.applicationContextFactory.implClassName";

	private ApplicationContextFactory applicationContextFactory;

	/**
	 * 根据配置初始化ApplicationContextFactory
	 */
	public void init(Properties configuration) {
		applicationContextFactory = getInstance(PROPKEY_APPLICATION_CONTEXT_FACTORY_CLASS_NAME, configuration);
	}

	/**
	 * No after initialization needed for this module
	 */
	public void afterInit() {
	}

	/**
	 * Gets the spring bean with the given name. The given test instance, by
	 * using {@link SpringApplicationContext}, determines the application
	 * context in which to look for the bean.
	 * <p/>
	 * A JTesterException is thrown when the no bean could be found for the
	 * given name.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param name
	 *            The name, not null
	 * @return The bean, not null
	 */
	public static Object getSpringBean(Object testObject, String name) {
		AbstractApplicationContext context = SpringModuleHelper.getContext(testObject);
		try {
			return context.getBean(name);
		} catch (BeansException e) {
			throw new JTesterException("Unable to get Spring bean. No Spring bean found for name " + name);
		}
	}

	/**
	 * 强制让springapplicationcontext失效，重新初始化
	 * 
	 * @param testedObject
	 */
	public void invalidateApplicationContext() {
		Object testedObject = this.applicationContextFactory.getTestedObject();
		SpringModuleHelper.closeSpringContext(testedObject);
		SpringModuleHelper.initSpringContext(testedObject, this.applicationContextFactory);
		SpringModuleHelper.injectTestedObjectSpringBean(testedObject);
	}

	/**
	 * Gets the spring bean with the given type. The given test instance, by
	 * using {@link SpringApplicationContext}, determines the application
	 * context in which to look for the bean. If more there is not exactly 1
	 * possible bean assignment, an JTesterException will be thrown.
	 * 
	 * @param testObject
	 *            The test instance, not null
	 * @param type
	 *            The type, not null
	 * @return The bean, not null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSpringBeanByType(Object testObject, Class<T> type) {
		AbstractApplicationContext context = SpringModuleHelper.getContext(testObject);
		Map<String, T> beans = context.getBeansOfType(type);
		if (beans == null || beans.size() == 0) {
			throw new JTesterException("Unable to get Spring bean by type. No Spring bean found for type "
					+ type.getSimpleName());
		}
		if (beans.size() > 1) {
			throw new JTesterException(
					"Unable to get Spring bean by type. More than one possible Spring bean for type "
							+ type.getSimpleName() + ". Possible beans; " + beans);
		}
		return beans.values().iterator().next();
	}

	/**
	 * 按照类型注入spring对象
	 * 
	 * @param testObject
	 */
	public static void injectSpringBeansByType(Object testObject) {
		Class<?> testedClazz = testObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, SpringBeanByType.class);
		for (Field field : fields) {
			try {
				Object bean = getSpringBeanByType(testObject, field.getType());
				ReflectUtil.setFieldValue(testObject, field, bean);
			} catch (Exception e) {
				String error = String.format("inject @SpringBeanByType field[%s] in class[%s] error.", field.getName(),
						testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}

	/**
	 * 根据@SpringBeanByName注入spring bean
	 * 
	 * @param testObject
	 */
	public static void injectSpringBeansByName(Object testObject) {
		Class<?> testedClazz = testObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, SpringBeanByName.class);
		for (Field field : fields) {
			try {
				SpringBeanByName byName = field.getAnnotation(SpringBeanByName.class);
				String beanName = field.getName();
				if (StringHelper.isBlankOrNull(byName.value()) == false) {
					beanName = byName.value();
				}
				Object bean = getSpringBean(testObject, beanName);
				ReflectUtil.setFieldValue(testObject, field, bean);
			} catch (Exception e) {
				String error = String.format("inject @SpringBeanByName field[%s] in class[%s] error.", field.getName(),
						testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}

	/**
	 * 清空按@SpringBeanByType方式注入的spring bean
	 * 
	 * @param testObject
	 */
	public static void cleanSpringBeansByType(Object testObject) {
		Class<?> testedClazz = testObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, SpringBeanByType.class);
		for (Field field : fields) {
			try {
				ReflectUtil.setFieldValue(testObject, field, null);
			} catch (Exception e) {
				String error = String.format("clean @SpringBeanByType field[%s] in class[%s] error.", field.getName(),
						testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}

	/**
	 * 清空根据@SpringBeanByName注入的spring bean
	 * 
	 * @param testObject
	 */
	public static void cleanSpringBeansByName(Object testObject) {
		Class<?> testedClazz = testObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, SpringBeanByName.class);
		for (Field field : fields) {
			try {
				ReflectUtil.setFieldValue(testObject, field, null);
			} catch (Exception e) {
				String error = String.format("clean @SpringBeanByName field[%s] in class[%s] error.", field.getName(),
						testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}

	public TestListener getTestListener() {
		return new JTesterSpringTestListener();
	}

	/**
	 * The {@link TestListener} for this module
	 */
	protected class JTesterSpringTestListener extends TestListener {
		public void afterCreateTestObject(Object testObject) {
			Class<?> claz = testObject.getClass();
			MockBeanRegister.registerAllMockBean(claz);

			long startTime = System.currentTimeMillis();
			boolean inited = SpringModuleHelper.initSpringContext(testObject, applicationContextFactory);
			if (inited) {
				long duration = System.currentTimeMillis() - startTime;
				JTesterLogger.info(String.format("take %d ms to init spring context for test obejct[%s]", duration,
						testObject.getClass().getName()));
				SpringModuleHelper.injectTestedObjectSpringBean(testObject);
			}
		}

		@Override
		public void afterTestClass(Object testObject) {
			if (!(testObject instanceof ISpringContextContainer)) {
				return;
			}
			ISpringContextContainer contextContainer = (ISpringContextContainer) testObject;
			Object context = contextContainer.getSpringContext();
			if (context != null) {
				JTesterLogger.warn("close spring context for class:" + testObject.getClass().getName());
				SpringModuleHelper.closeSpringContext(this);
			}
		}
	}
}
