package org.jtester.module.spring.strategy.register;

import java.util.Queue;

import org.jtester.exception.FindBeanImplClassException;
import org.jtester.reflector.helper.ClazzConst;
import org.jtester.reflector.helper.ClazzHelper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;

/**
 * 属性依赖注册
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class PropertiesRegister {
	protected final Class ownerClazz;
	protected final BeanDefinitionRegister definitionRegister;

	protected PropertiesRegister(final Class ownerClazz, final BeanDefinitionRegister definitionRegister) {
		this.ownerClazz = ownerClazz;
		this.definitionRegister = definitionRegister;
	}

	public abstract void registerProperties(final Queue<Class> registedBeanClazz);

	/**
	 * 注册ownerClazz的属性bean
	 * 
	 * @param ownerClazz
	 * @param definitionRegister
	 * @param registedBeanClazz
	 *            以注册的spring bean实现类列表
	 */
	public static void registerPropertiesBean(final Class ownerClazz, final BeanDefinitionRegister definitionRegister,
			final Queue<Class> registedBeanClazz) {
		new MethodPropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);

		boolean isResourceAvailable = ClazzHelper.isClassAvailable(ClazzConst.Javax_Resource_Annotation);
		if (isResourceAvailable) {
			new ResourcePropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);
		}

		boolean isAutowiredAvailable = ClazzHelper.isClassAvailable(ClazzConst.Spring_Autowired_Annotation);
		if (isAutowiredAvailable) {
			new AutowiredPropertiesRegister(ownerClazz, definitionRegister).registerProperties(registedBeanClazz);
		}
	}

	protected void registerBean(final String beanName, final Class propClazz, final Queue<Class> registedBeanClazz) {
		try {
			boolean doesRegisted = definitionRegister.doesHaveRegisted(beanName);
			if (doesRegisted) {
				return;
			}
			Class impl = definitionRegister.findImplementClass(ownerClazz, beanName, propClazz);
			if (impl == null) {
				return;
			}
			// AbstractBeanDefinition beanDefinition =
			// SpringBeanRegister.getAnnotatedGenericBeanDefinition(beanName,
			// impl, true);
			AbstractBeanDefinition beanDefinition = SpringBeanRegister.getRootBeanDefinition(beanName, impl, true);
			definitionRegister.register(beanName, beanDefinition);

			registedBeanClazz.offer(impl);
		} catch (FindBeanImplClassException e) {
			definitionRegister.ignoreNotFoundException(e);
		}
	}
}
