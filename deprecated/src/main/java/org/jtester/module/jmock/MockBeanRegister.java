package org.jtester.module.jmock;

import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jtester.annotations.MockBean;
import org.jtester.utility.StringHelper;

@SuppressWarnings({ "deprecation", "rawtypes", "unchecked" })
public class MockBeanRegister {
	private static ThreadLocal<Map<String, Field>> nameRegister = new ThreadLocal<Map<String, Field>>();

	private static ThreadLocal<Map<String, Field>> typeRegister = new ThreadLocal<Map<String, Field>>();

	/**
	 * 注册tested object下所有的MockBean对象<br>
	 * 目前只能注册接口类的mockBean，以后考虑class类的 TODO
	 * 
	 * @param testedObject
	 */
	public static void registerAllMockBean(Class testedClazz) {
		MockBeanRegister.cleanRegister();
		Set<Field> mockBeansByName = getFieldsAnnotatedWith(testedClazz,
				MockBean.class);
		// JMock mock bean
		for (Field mockField : mockBeansByName) {
			MockBean mock = mockField.getAnnotation(MockBean.class);

			String beanName = mock.bean();
			if (StringHelper.isBlankOrNull(beanName)) {
				beanName = mockField.getName();
			}
			MockBeanRegister.register(beanName, mockField);
		}
	}

	private static void register(String name, Field field) {
		if (StringHelper.isBlankOrNull(name) == false && field != null) {
			field.setAccessible(true);
			currThreadRegistedByName().put(name, field);
		}
	}

	/**
	 * 清除注册信息
	 */
	public static void cleanRegister() {
		currThreadRegistedByName().clear();
		currThreadRegistedByType().clear();
	}

	/**
	 * 取出名称为name的mockbean
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public static Field getRegistedField(String name) {
		Map<String, Field> byNames = currThreadRegistedByName();
		return byNames.get(name);
	}

	/**
	 * 取出类型为clazz的mockbean
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field getRegistedFieldByType(String clazz) {
		Map<String, Field> byTypes = currThreadRegistedByType();
		return byTypes.get(clazz);
	}

	/**
	 * 判断是否已经注册的名称为name的mockbean
	 * 
	 * @param name
	 * @return
	 */
	public static boolean hasRegistedName(String name) {
		Map<String, Field> byNames = currThreadRegistedByName();
		return byNames.containsKey(name);
	}

	/**
	 * 判断是否注册有class全称为clazz的mock bean
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean hasRegistedType(String clazz) {
		if (clazz == null) {
			return false;
		}
		Map<String, Field> byTypes = currThreadRegistedByType();
		return byTypes.containsKey(clazz);
	}

	private static Map<String, Field> currThreadRegistedByName() {
		Map<String, Field> map = nameRegister.get();
		if (map == null) {
			map = new HashMap<String, Field>();
			nameRegister.set(map);
		}
		return map;
	}

	private static Map<String, Field> currThreadRegistedByType() {
		Map<String, Field> map = typeRegister.get();
		if (map == null) {
			map = new HashMap<String, Field>();
			typeRegister.set(map);
		}
		return map;
	}
}
