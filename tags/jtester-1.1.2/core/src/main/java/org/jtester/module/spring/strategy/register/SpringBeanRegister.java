package org.jtester.module.spring.strategy.register;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.bytecode.reflector.helper.ClazzHelper;
import org.jtester.exception.FindBeanImplClassException;
import org.jtester.module.spring.ImplementorFinder;
import org.jtester.utility.AnnotationUtils;
import org.jtester.utility.StringHelper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * 动态配置spring context的bean @AutoBeanInject<br>
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SpringBeanRegister {

	Queue<Class> beanFields = new LinkedList<Class>();

	/**
	 * 动态注册@SpringBeanByName,@SpringBeanByType和@SpringBeanFor等注解定义的bean
	 * 
	 * @param beanFactory
	 * @param testedClazz
	 */
	public static void dynamicRegisterBeanDefinition(final DefaultListableBeanFactory beanFactory,
			final Class testedClazz) {
		SpringBeanRegister dynamicBean = new SpringBeanRegister(beanFactory, testedClazz);
		try {
			dynamicBean.registerSpringBean();
		} catch (FindBeanImplClassException e) {
			throw new RuntimeException(e);
		}
	}

	private final DefaultListableBeanFactory beanFactory;
	private final Class testedClazz;
	private final BeanDefinitionRegister definitionRegister;

	SpringBeanRegister(final DefaultListableBeanFactory beanFactory, final Class testedClazz) {
		if (testedClazz == null) {
			throw new RuntimeException("Current thread hasn't registered tested class!");
		}
		this.beanFactory = beanFactory;
		this.testedClazz = testedClazz;
		AutoBeanInject autoBeanInject = AnnotationUtils.getClassLevelAnnotation(AutoBeanInject.class, testedClazz);
		this.definitionRegister = new BeanDefinitionRegister(this.beanFactory, autoBeanInject);
	}

	/**
	 * 注册@SpringBeanByName、@SpringBean和@SpringBeanFor 定义的spring bean对象
	 * 
	 * @param beanFactory
	 * @param testedClazz
	 * @throws FindBeanImplClassException
	 */
	protected final void registerSpringBean() throws FindBeanImplClassException {
		// o1 注册@SpringBeanByName注释的spring bean
		Set<Field> fields_SpringBeanByName = AnnotationUtils
				.getFieldsAnnotatedWith(testedClazz, SpringBeanByName.class);
		for (Field field : fields_SpringBeanByName) {
			SpringBeanByName byName = field.getAnnotation(SpringBeanByName.class);

			String beanName = field.getName();
			if (StringHelper.isBlankOrNull(byName.value()) == false) {
				beanName = byName.value();
			}
			String initMethod = byName.init();
			Class claz = byName.claz();
			if (claz != SpringBeanByName.class && ClazzHelper.isInterfaceOrAbstract(claz) == false) {
				RootBeanDefinition beanDefinition = SpringBeanRegister.getRootBeanDefinition(beanName, claz,
						initMethod, false);
				this.definitionRegister.register(beanName, beanDefinition);

				beanFields.offer(claz);
			} else if (this.definitionRegister.allowAutoInject(beanName)) {
				Class impl = definitionRegister.findImplementClass(testedClazz, beanName, field.getType());
				if (impl == null) {
					continue;
				}
				RootBeanDefinition beanDefinition = SpringBeanRegister.getRootBeanDefinition(beanName, impl,
						initMethod, false);
				definitionRegister.register(beanName, beanDefinition);
				beanFields.offer(impl);
			}
		}

		// o2 注册@SpringBeanByType注释的spring bean
		Set<Field> fields_SpringBeanByType = AnnotationUtils
				.getFieldsAnnotatedWith(testedClazz, SpringBeanByType.class);
		for (Field field : fields_SpringBeanByType) {
			SpringBeanByType byType = field.getAnnotation(SpringBeanByType.class);

			Class claz = byType.value();
			String initMethod = byType.init();
			String beanName = field.getName();
			if (claz != SpringBeanByType.class && ClazzHelper.isInterfaceOrAbstract(claz) == false) {
				RootBeanDefinition beanDefinition = SpringBeanRegister.getRootBeanDefinition(beanName, claz,
						initMethod, false);
				this.definitionRegister.register(beanName, beanDefinition);

				beanFields.offer(claz);
			} else if (this.definitionRegister.allowAutoInject(beanName)) {
				Class impl = definitionRegister.findImplementClass(testedClazz, beanName, field.getType());
				if (impl == null) {
					continue;
				}
				RootBeanDefinition beanDefinition = SpringBeanRegister.getRootBeanDefinition(beanName, impl,
						initMethod, false);
				definitionRegister.register(beanName, beanDefinition);
				beanFields.offer(impl);
			}
		}

		if (this.definitionRegister.allowAutoInject() == true) {
			/**
			 * 属性的注入放到所有的@SpringBean和@SpringBeanByName注入完毕后开始
			 */
			Class clazz = beanFields.poll();
			while (clazz != null) {
				if (clazz != null) {
					PropertiesRegister.registerPropertiesBean(clazz, definitionRegister, this.beanFields);
				}
				clazz = beanFields.poll();
			}
		}
	}

	/**
	 * 返回普通方式定义的BeanDefinition
	 * 
	 * @param beanName
	 * @param implClazz
	 * @param initMethod
	 * @param isLazy
	 * @return
	 */
	public static RootBeanDefinition getRootBeanDefinition(String beanName, Class implClazz, String initMethod,
			boolean isLazy) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClassName(implClazz.getName());
		beanDefinition.setScope("singleton");
		beanDefinition.setAutowireCandidate(true);
		beanDefinition.setLazyInit(isLazy);

		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

		String init_method = initMethod;
		if (StringHelper.isBlankOrNull(initMethod)) {
			init_method = ImplementorFinder.findInitMethodName(implClazz);
		}
		if (StringHelper.isBlankOrNull(init_method) == false) {
			beanDefinition.setInitMethodName(init_method);
		}

		return beanDefinition;
	}

	// /**
	// * 返回普通方式定义的BeanDefinition
	// *
	// * @param beanName
	// * @param implClazz
	// * @param isLazy
	// * @return
	// */
	// public static AnnotatedGenericBeanDefinition
	// getAnnotatedGenericBeanDefinition(String beanName, Class implClazz,
	// boolean isLazy) {
	// AnnotatedGenericBeanDefinition beanDefinition = new
	// AnnotatedGenericBeanDefinition(implClazz);
	// // beanDefinition.setBeanClassName(implClazz.getName());
	// beanDefinition.setScope("singleton");
	// beanDefinition.setAutowireCandidate(true);
	// beanDefinition.setLazyInit(isLazy);
	//
	// beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
	//
	// String initMethodName = ImplementorFinder.findInitMethodName(implClazz);
	// if (initMethodName != null && "".equals(initMethodName.trim()) == false)
	// {
	// beanDefinition.setInitMethodName(initMethodName);
	// }
	//
	// return beanDefinition;
	// }
}
