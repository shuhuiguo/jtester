package org.jtester.module.core;

import static org.jtester.utility.AnnotationUtils.getFieldsAnnotatedWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import org.jmock.Mockery;
import org.jmock.api.MockObjectNamingScheme;
import org.jmock.lib.CamelCaseNamingScheme;
import org.jmock.lib.legacy.ClassImposteriser;
import org.jtester.annotations.Mock;
import org.jtester.annotations.MockBean;
import org.jtester.bytecode.reflector.helper.FieldHelper;
import org.jtester.module.TestListener;
import org.jtester.utility.StringHelper;

/**
 * please use jmockit framework
 * 
 * @author darui.wudr
 * 
 */
@Deprecated
@SuppressWarnings("rawtypes")
public class JmockModule implements Module {
	private Mockery context;

	private MockObjectNamingScheme namingScheme = CamelCaseNamingScheme.INSTANCE;

	public void init() {
		;
	}

	public void afterInit() {
		;
	}

	public TestListener getTestListener() {
		return new JMockTestListener();
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
	@SuppressWarnings("unchecked")
	private Object mock(Object testedObject, String mockname, Field field) {
		Class mockType = field.getType();
		if (StringHelper.isBlankOrNull(mockname)) {
			mockname = namingScheme.defaultNameFor(mockType);
			mockname += "#" + field.getName();
		}
		mockname = mockname + "#" + Thread.currentThread().getId();
		Object mockObject = context.mock(mockType, mockname);

		FieldHelper.setFieldValue(testedObject, field, mockObject);
		return mockObject;
	}

	protected class JMockTestListener extends TestListener {
		@Override
		public void setupMethod(Object testObject, Method testMethod) throws Exception {
			context = new Mockery() {
				{
					setImposteriser(ClassImposteriser.INSTANCE);
				}
			};
			createMocks(testObject);
		}

		@Override
		public void afterMethodRunned(Object testObject, Method testMethod, Throwable testThrowable) throws Exception {
			context.assertIsSatisfied();
		}

		@Override
		protected String getName() {
			return "JMockTestListener";
		}
	}
}
