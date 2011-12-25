package org.jtester.junit.internal;

import java.lang.reflect.Method;

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
			return getMethod().getName() + toParaString(parameters);
		}
	}

	static String toParaString(Object[] paras) {
		if (paras == null)
			return "null";
		if (paras.length == 0)
			return "[]";

		StringBuilder buf = new StringBuilder();

		for (int index = 0; index < paras.length; index++) {
			if (index == 0) {
				buf.append('[');
			} else {
				buf.append(", ");
			}
			String value = toObjectString(paras[index]);
			buf.append(value);
		}

		buf.append("]");
		return buf.toString();
	}

	/**
	 * 截断太长的字符串<br>
	 * 替换回车等字符,测试方法中如果有回车符，会导致junit框架无法识别
	 * 
	 * @param obj
	 * @return
	 */
	static String toObjectString(Object obj) {
		String value = String.valueOf(obj);
		if (value.length() > 20) {
			value = value.substring(0, 20);
		}
		value = value.replaceAll("\\s+", " ");
		return value;
	}
}
