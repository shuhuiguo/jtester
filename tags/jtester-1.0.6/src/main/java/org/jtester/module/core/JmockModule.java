package org.jtester.module.core;

import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;
import static org.jtester.utility.ReflectionUtils.setFieldValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jmock.Mockery;
import org.jmock.api.MockObjectNamingScheme;
import org.jmock.lib.CamelCaseNamingScheme;
import org.jmock.lib.legacy.ClassImposteriser;
import org.jtester.annotations.deprecated.Mock;
import org.jtester.annotations.deprecated.MockBean;
import org.jtester.module.TestListener;

/**
 * please use jmockit framework
 * 
 * @author darui.wudr
 * 
 */
@Deprecated
public class JmockModule implements Module {
	private Mockery context;

	private MockObjectNamingScheme namingScheme = CamelCaseNamingScheme.INSTANCE;

	public void afterInit() {
		;
	}

	public TestListener getTestListener() {
		return new JmockTestListener();
	}

	public void init(Properties configuration) {
		;
	}

	public Mockery getMockery() {
		return this.context;
	}

	/**
	 * 创建Mock对象
	 * 
	 * @param testedObject
	 */
	private void createMocks(Object testedObject) {
		Set<Field> mockFields = getFieldsAnnotatedWith(testedObject.getClass(), Mock.class);
		for (Field mockField : mockFields) {
			Mock mock = mockField.getAnnotation(Mock.class);
			this.mock(testedObject, mock.value(), mockField);
		}

		Set<Field> mockBeansByName = getFieldsAnnotatedWith(testedObject.getClass(), MockBean.class);

		for (Field mockField : mockBeansByName) {
			MockBean mock = mockField.getAnnotation(MockBean.class);
			this.mock(testedObject, mock.value(), mockField);
		}
	}

	/**
	 * 根据@Mock,@MockBean的value属性创建Mock对象
	 * 
	 * @param testedObject
	 * @param mockname
	 * @param field
	 * @return
	 */
	private Object mock(Object testedObject, String mockname, Field field) {
		Class<?> mockType = field.getType();
		if (StringUtils.isBlank(mockname)) {
			mockname = namingScheme.defaultNameFor(mockType);
			mockname += "#" + field.getName();
		}
		mockname = mockname + "#" + Thread.currentThread().getId();
		Object mockObject = context.mock(mockType, mockname);

		setFieldValue(testedObject, field, mockObject);
		return mockObject;
	}

	protected class JmockTestListener extends TestListener {
		@Override
		public void beforeTestSetUp(Object testObject, Method testMethod) {
			context = new Mockery() {
				{
					setImposteriser(ClassImposteriser.INSTANCE);
				}
			};
			createMocks(testObject);
		}

		@Override
		public void afterTestMethod(Object testObject, Method testMethod, Throwable throwable) {
			context.assertIsSatisfied();
		}
	}
}
