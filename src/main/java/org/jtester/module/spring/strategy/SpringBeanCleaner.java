package org.jtester.module.spring.strategy;

import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.exception.JTesterException;
import org.jtester.reflector.FieldAccessor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SpringBeanCleaner {
	/**
	 * 清空测试实例中spring bean的引用
	 * 
	 * @param testedObject
	 */
	public static void cleanSpringBeans(Object testedObject) {
		cleanSpringBeansByAnnotation(testedObject, SpringBeanByName.class);
		cleanSpringBeansByAnnotation(testedObject, SpringBeanByType.class);
	}

	/**
	 * 把测试实例中的 @SpringBeanByType 或 @SpringBeanByType 的字段置空
	 * 
	 * @param testObject
	 */
	private static void cleanSpringBeansByAnnotation(Object testedObject, Class<? extends Annotation> annotation) {
		Class testedClazz = testedObject.getClass();
		Set<Field> fields = getFieldsAnnotatedWith(testedClazz, annotation);
		for (Field field : fields) {
			try {
				FieldAccessor.setFieldValue(testedObject, field, null);
			} catch (Exception e) {
				String error = String.format("clean @%s field[%s] in class[%s] error.", annotation.getName(),
						field.getName(), testedClazz.getName());
				throw new JTesterException(error, e);
			}
		}
	}
}
