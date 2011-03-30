package org.jtester.module.spring;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jtester.annotations.AutoBeanInject;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.annotations.AutoBeanInject.BeanMap;
import org.jtester.exception.FindBeanImplClassException;
import org.jtester.module.jmock.MockBeanRegister;
import org.jtester.reflector.helper.ClazzHelper;
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
	private final static Logger log4j = Logger.getLogger(SpringBeanRegister.class);

	Queue<Class> beanFields = new LinkedList<Class>();

	/**
	 * 排除掉的属性
	 */
	private static Set<String> excludeProperties = new HashSet<String>() {
		private static final long serialVersionUID = 705657590280935844L;
		{
			add("class");
			add("override");
		}
	};

	private static Set<String> excludePackages = new HashSet<String>() {
		private static final long serialVersionUID = -102059987060939894L;
		{
			add("org.springframework.");
			add("java.");
			add("javax.");
			add("org.spring.");
		}
	};

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
			dynamicBean.dynamicRegister();
		} catch (FindBeanImplClassException e) {
			throw new RuntimeException(e);
		}
	}

	private final DefaultListableBeanFactory beanFactory;
	private final Class testedClazz;
	private final AutoBeanInject autoBeanInject;
	private List<BeanMap> beanClazzMapping;

	SpringBeanRegister(final DefaultListableBeanFactory beanFactory, final Class testedClazz) {
		this.beanFactory = beanFactory;
		this.testedClazz = testedClazz;
		if (testedClazz != null) {
			autoBeanInject = AnnotationUtils.getClassLevelAnnotation(AutoBeanInject.class, testedClazz);
		} else {
			throw new RuntimeException("Current thread hasn't registered tested class!");
		}
	}

	/**
	 * 动态注册当前测试类的spring bean定义
	 * 
	 * @param beanFactory
	 * @return 返回当前的测试类class
	 * @throws FindBeanImplClassException
	 */
	protected void dynamicRegister() throws FindBeanImplClassException {
		if (canInject()) {
			log4j.info(String.format("tested class[%s] will be auto config spring bean!", testedClazz.getName()));
			this.beanClazzMapping = checkBeanMap(autoBeanInject.maps());
		}
		registerSpringBean();
	}

	/**
	 * 判断是否允许自动注入接口bean(自动查找实现)
	 * 
	 * @return
	 */
	private boolean canInject() {
		if (autoBeanInject == null || autoBeanInject.value() == false) {
			return false;
		} else {
			return true;
		}
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
			Class claz = byName.claz();
			if (claz != SpringBeanByName.class && ClazzHelper.isInterfaceOrAbstract(claz) == false) {
				claz = registerBeanDefinition(beanName, testedClazz, claz, false, true);
				beanFields.offer(claz);
			} else if (canInject()) {
				claz = field.getType();
				claz = registerBeanDefinition(beanName, testedClazz, field.getType(), false, false);
				beanFields.offer(claz);
			}
		}

		// o2 注册@SpringBeanByType注释的spring bean
		Set<Field> fields_SpringBeanByType = AnnotationUtils
				.getFieldsAnnotatedWith(testedClazz, SpringBeanByType.class);
		for (Field field : fields_SpringBeanByType) {
			SpringBeanByType byType = field.getAnnotation(SpringBeanByType.class);

			Class claz = byType.value();
			String beanName = field.getName();
			if (claz != SpringBeanByType.class && ClazzHelper.isInterfaceOrAbstract(claz) == false) {
				claz = registerBeanDefinition(beanName, testedClazz, field.getType(), false, true);
				beanFields.offer(claz);
			} else if (canInject()) {
				claz = field.getType();
				claz = registerBeanDefinition(beanName, testedClazz, field.getType(), false, false);
				beanFields.offer(claz);
			}
		}

		if (canInject() == false) {
			return;
		}

		/**
		 * 属性的注入放到所有的@SpringBean和@SpringBeanByName注入完毕后开始
		 */
		Class clazz = beanFields.poll();
		while (clazz != null) {
			if (clazz != null) {
				injectPropertyBean(clazz);
			}
			clazz = beanFields.poll();
		}
	}

	/**
	 * 注册spring bean定义
	 * 
	 * @param beanName
	 *            spring bean的名称
	 * @param ownerClazz
	 *            拥有这个bean的类（一般是测试类或 OwnerClazz.setSpringBean(bean)的类
	 * @param fieldType
	 *            spring bean的字段类型( FieldType bean)
	 * @param lazyInit
	 *            是否懒加载?
	 * @param override
	 *            是否覆盖spring容器中原有的定义
	 * @return
	 * @throws FindBeanImplClassException
	 *             查找实现类失败，无法注册spring bean定义
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	protected Class registerBeanDefinition(final String beanName, final Class ownerClazz, final Class fieldType,
			boolean lazyInit, boolean override) throws FindBeanImplClassException {
		if (beanFactory.containsBeanDefinition(beanName) && override == false) {
			log4j.info(String.format("spring bean[%s] has been defined in application context!", beanName));
			return null;
		}

		Class beanClazz = ImplementorFinder.findImplClazz(ownerClazz, beanName, fieldType, beanClazzMapping);
		if (beanClazz == null || ClazzHelper.isInterfaceOrAbstract(beanClazz)) {
			return null;
		}
		try {
			Constructor c = beanClazz.getConstructor(new Class[] {});
			if (c == null) {
				return null;
			}
		} catch (Exception e) {
			String error = String.format("find default constructor function of class[%s] error.", beanClazz.getName());
			throw new FindBeanImplClassException(error, e);
		}

		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClassName(beanClazz.getName());
		beanDefinition.setScope("singleton");
		beanDefinition.setAutowireCandidate(true);
		beanDefinition.setLazyInit(lazyInit);

		beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);

		String initMethodName = ImplementorFinder.findInitMethodName(beanClazz);
		if (initMethodName != null && "".equals(initMethodName.trim()) == false) {
			beanDefinition.setInitMethodName(initMethodName);
		}

		beanFactory.registerBeanDefinition(beanName, beanDefinition);
		return beanClazz;
	}

	/**
	 * 自动注入clazz属性bean definition<br>
	 * 下列情况会终止依赖的自动注入<br>
	 * o 依赖的名称(beanName)是mock对象名称<br>
	 * o AutoBeanInject中显示排除掉的属性的类型package<br>
	 * o java和spring package的变量<br>
	 * o primitive变量
	 * 
	 * @param beanFactory
	 * @param ownerClazz
	 */
	protected void injectPropertyBean(final Class ownerClazz) {
		Method[] all = ownerClazz.getMethods();
		for (Method method : all) {
			if (method.getParameterTypes().length != 1) {
				continue;
			}

			Class propClazz = method.getParameterTypes()[0];
			if (propClazz.isEnum() || propClazz.isEnum() || propClazz.isArray() || propClazz.isPrimitive()) {
				continue;
			}
			if (isExcludePackage(propClazz.getName())) {
				continue;
			}

			String beanName = ClazzHelper.exactBeanName(method);

			if (MockBeanRegister.hasRegistedName(beanName)) {
				continue;
			}
			if (this.isExcludeProperty(beanName)) {
				continue;
			}
			if (propClazz.isPrimitive()) {
				continue;
			}

			try {
				Class implClazz = registerBeanDefinition(beanName, ownerClazz, propClazz, true, false);
				if (implClazz != null) {
					this.beanFields.offer(implClazz);
				}
			} catch (FindBeanImplClassException e) {
				boolean ignore = this.autoBeanInject.ignoreNotFound();
				if (ignore) {
					log4j.warn("ignore NotFound:" + e.getMessage());
				} else {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private final static String VALID_PACK_REGEX = "[\\w_$\\.\\*]+";

	/**
	 * 验证@BeanMap属性的有效性
	 * 
	 * @param autoMaps
	 * @return
	 */
	private static List<BeanMap> checkBeanMap(BeanMap[] autoMaps) {
		List<BeanMap> mapping = new ArrayList<BeanMap>();
		if (autoMaps == null || autoMaps.length == 0) {
			return mapping;
		}
		for (BeanMap autoMap : autoMaps) {
			String intf = autoMap.intf();
			String impl = autoMap.impl();
			if (StringHelper.isBlankOrNull(intf) || StringHelper.isBlankOrNull(impl)) {
				throw new RuntimeException(String.format("Illegal value @AutoMap(intf=\"%s\",impl=\"%s\") value", intf,
						impl));
			}
			if (intf.matches(VALID_PACK_REGEX) == false || impl.matches(VALID_PACK_REGEX) == false) {
				throw new RuntimeException(String.format("Illegal value @AutoMap(intf=\"%s\",impl=\"%s\") value", intf,
						impl));
			}
			mapping.add(autoMap);
		}
		return mapping;
	}

	/**
	 * 是否是被排除注入的属性
	 * 
	 * @param property
	 * @return
	 */
	private boolean isExcludeProperty(String property) {
		if (StringHelper.isBlankOrNull(property)) {
			return true;
		}
		if (excludeProperties.contains(property) || beanFactory.containsBeanDefinition(property)) {
			return true;
		}
		String[] excludeProperties = this.autoBeanInject.excludeProperties();
		for (String excludeProperty : excludeProperties) {
			if (property.equals(excludeProperty)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 以下属性不能自动注入<br>
	 * o spring本身提供的bean必须在文件中配置 <br>
	 * o java.* 和 javax.* package<br>
	 * o @AutoBeanInject 中显式排除的package
	 */
	private boolean isExcludePackage(String clazzname) {
		for (String excludePackage : excludePackages) {
			if (clazzname.startsWith(excludePackage)) {
				return true;
			}
		}
		String[] excludePackages = this.autoBeanInject.excludePackages();
		if (excludePackages == null) {
			return false;
		}
		for (String excludePackage : excludePackages) {
			String regex = ClazzHelper.getPackageRegex(excludePackage);
			if (clazzname.matches(regex)) {
				return true;
			}
		}
		return false;
	}
}
