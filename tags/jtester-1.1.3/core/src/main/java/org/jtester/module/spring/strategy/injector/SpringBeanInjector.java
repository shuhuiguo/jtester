package org.jtester.module.spring.strategy.injector;

import java.lang.annotation.Annotation;

import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * 查找spring策略接口，以及策略工厂类
 * 
 * @author darui.wudr
 * 
 */
public abstract class SpringBeanInjector {

	private final static SpringBeanInjector byName = new SpringBeanInjectorByName();

	private final static SpringBeanInjector byType = new SpringBeanInjectorByType();

	/**
	 * 往测试类实例中注入spring bean
	 * 
	 * @param testedObject
	 */
	public static void injectSpringBeans(Object springContext, Object testedObject) {
		if (springContext instanceof AbstractApplicationContext) {
			AbstractApplicationContext context = (AbstractApplicationContext) springContext;
			byName.injectBy(context, testedObject, SpringBeanByName.class);
			byType.injectBy(context, testedObject, SpringBeanByType.class);
		} else {
			throw new RuntimeException(String.format(
					"the type error, object[%s] isn't an instance of SpringApplicationContext.",
					springContext == null ? null : springContext.getClass().getName()));
		}
	}

	/**
	 * 按Annotation注释注入spring bean到测试实例中
	 * 
	 * @param context
	 *            spring容器
	 * @param testedObject
	 *            测试类
	 * @param annotation
	 *            字段声明的Annotation
	 * @return
	 */
	public abstract void injectBy(AbstractApplicationContext context, Object testedObject,
			Class<? extends Annotation> annotation);
}
