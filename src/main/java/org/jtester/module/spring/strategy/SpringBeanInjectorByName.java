package org.jtester.module.spring.strategy;

import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.exception.JTesterException;
import org.jtester.reflector.FieldAccessor;
import org.jtester.utility.StringHelper;
import org.springframework.context.support.AbstractApplicationContext;

@SuppressWarnings({ "unchecked", "rawtypes" })
class SpringBeanInjectorByName extends SpringBeanInjector {
	/**
	 * {@inheritDoc}<br>
	 * <br>
	 * 根据@SpringBeanByName注入spring bean<br>
	 * <br>
	 * Gets the spring bean with the given name. The given test instance, by
	 * using {@link SpringApplicationContext}, determines the application
	 * context in which to look for the bean.
	 * <p/>
	 * A JTesterException is thrown when the no bean could be found for the
	 * given name.
	 */
	public void injectBy(AbstractApplicationContext context, Object testedObject, Class<? extends Annotation> annotation) {
		Class testedClazz = testedObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, SpringBeanByName.class);
		for (Field field : fields) {
			try {
				SpringBeanByName byName = field.getAnnotation(SpringBeanByName.class);
				String beanName = byName.value();
				if (StringHelper.isBlankOrNull(byName.value())) {
					beanName = field.getName();
				}
				Object bean = context.getBean(beanName);
				FieldAccessor.setFieldValue(testedObject, field, bean);
			} catch (Exception e) {
				String error = String.format("inject @SpringBeanByName field[%s] in class[%s] error.", field.getName(),
						testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}
}
