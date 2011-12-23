package org.jtester.junit.internal;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.runners.model.FrameworkMethod;

public class FrameworkMethodWithParameters extends FrameworkMethod {
	private Object[] parameters;

	public FrameworkMethodWithParameters(Method method, Object[] parameters) {
		super(method);
		this.parameters = parameters;
	}

	@Override
	public Object invokeExplosively(Object target, Object... parameters) throws Throwable {
		return super.invokeExplosively(target, this.parameters);
	}

	@Override
	public String toString() {
		if (this.parameters == null || this.parameters.length == 0) {
			return super.toString();
		} else {
			return getMethod().getName() + Arrays.toString(parameters);
		}
	}
}
