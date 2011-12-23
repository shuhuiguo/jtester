package org.jtester.core.junit.internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jtester.core.junit.DataFrom;
import org.jtester.reflector.utility.MethodHelper;

@SuppressWarnings("rawtypes")
public class ParameterDataFromHelper {
	/**
	 * 构造一系列有参的测试方法
	 * 
	 * @param testMethod
	 * @return
	 */
	public static List<FrameworkMethodWithParameters> computeParameterizedTestMethods(Method testMethod,
			DataFrom dataFrom) {
		String value = dataFrom.value();
		if ("".equals(value)) {
			throw new RuntimeException("You should specify the value property of @DataFrom() item.");
		}
		switch (dataFrom.source()) {
		case FromMethod:
			Class dataFromClaz = dataFrom.clazz();
			if (dataFromClaz == DataFrom.class) {
				dataFromClaz = testMethod.getDeclaringClass();
			}
			return computeParameterziedFromDataProviderMethod(testMethod, value, dataFromClaz);
		case FromFile:
			// TODO
		default:
			throw new RuntimeException("umimplement the data from uri mode.");
		}
	}

	private static List<FrameworkMethodWithParameters> computeParameterziedFromDataProviderMethod(Method testMethod,
			String dataFromMethod, Class dataFromClaz) {
		Object data = MethodHelper.invokeStatic(dataFromClaz, dataFromMethod);
		if (data instanceof Iterator) {
			return computeParameterFromIterator(testMethod, (Iterator) data);
		} else {
			throw new RuntimeException("The @DataFrom method can only return value of type Iterator<Object[]>.");
		}
	}

	private static List<FrameworkMethodWithParameters> computeParameterFromIterator(Method method, Iterator iterator) {
		List<FrameworkMethodWithParameters> methodWithParameters = new ArrayList<FrameworkMethodWithParameters>();
		for (; iterator.hasNext();) {
			Object caseData = iterator.next();
			if (caseData instanceof Object[]) {
				methodWithParameters.add(new FrameworkMethodWithParameters(method, (Object[]) caseData));
			} else {
				methodWithParameters.add(new FrameworkMethodWithParameters(method, new Object[] { caseData }));
			}
		}
		return methodWithParameters;
	}
}
