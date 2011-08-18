package org.jtester.exception;

/**
 * 异常包装器
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings("rawtypes")
public class ExceptionWrapper {
	/**
	 * 把Throwable包装为运行时异常抛出<br>
	 * 若throwable==null，则什么事都不干
	 * 
	 * @param throwable
	 */
	public static void throwRuntimeException(Throwable throwable) {
		if (throwable == null) {
			return;
		}
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		} else if (throwable instanceof Error) {
			throw (Error) throwable;
		} else {
			throw new RuntimeException(throwable);
		}
	}

	/**
	 * 把Throwable包装为运行时异常抛出<br>
	 * 若throwable==null，则什么事都不干
	 * 
	 * @param error
	 *            附加的异常消息，使异常信息更明确
	 * @param throwable
	 */
	public static void throwRuntimeException(String error, Throwable throwable) {
		if (throwable == null) {
			return;
		}
		throw new RuntimeException(error, throwable);
	}

	/**
	 * 包装advised的异常信息，使提示更明显
	 * 
	 * @param advised
	 * @param e
	 */
	public static RuntimeException wrapdAdvisedException(Object object, Exception e) {
		if (!(object instanceof org.springframework.aop.framework.Advised)) {
			return new RuntimeException(e);
		}
		org.springframework.aop.framework.Advised advised = (org.springframework.aop.framework.Advised) object;
		StringBuilder sb = new StringBuilder();

		sb.append("value[" + object + "] is org.springframework.aop.framework.Advised\n");
		if (advised.isProxyTargetClass()) {
			sb.append("proxied by the full target class");
			return new RuntimeException(sb.toString(), e);
		} else {
			Class[] clazzes = ((org.springframework.aop.framework.Advised) object).getProxiedInterfaces();
			sb.append("proxyed by the interfaces:\n");
			for (Class clazz : clazzes) {
				sb.append("\t" + clazz.getName() + "\n");
			}
			return new RuntimeException(sb.toString(), e);
		}
	}
}
