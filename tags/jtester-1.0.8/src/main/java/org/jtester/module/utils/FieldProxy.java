package org.jtester.module.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jtester.module.spring.proxy.ClassImposteriser;
import org.jtester.module.spring.proxy.Imposteriser;
import org.jtester.module.spring.proxy.Invocation;
import org.jtester.module.spring.proxy.Invokable;
import org.jtester.reflector.FieldAccessor;

/**
 * 目标对象字段的代理<br>
 * 用于运行时动态获得目标对象字段的实际值调用<br>
 * see @SpringBeanFor <br>
 * see @Inject
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FieldProxy implements Invokable {
	private final String fieldName;

	private final FieldAccessor accessor;

	private final Object testedObject;

	protected FieldProxy(final Object testedObject, final String fieldName) {
		this.fieldName = fieldName;
		this.accessor = new FieldAccessor(fieldName, testedObject.getClass());
		this.testedObject = testedObject;
	}

	public Object invoke(Invocation invocation) throws Throwable {
		Object fieldValue = accessor.get(testedObject);
		if (fieldValue == null) {
			throw new NullPointerException(String.format("field[%s] value of object[%s] is null.", fieldName,
					testedObject.getClass().getName()));
		}
		try {
			Method method = invocation.getInvokedMethod();
			Object[] paras = invocation.getParametersAsArray();
			boolean accessible = method.isAccessible();
			if (accessible == false) {
				method.setAccessible(true);
			}
			Object o = method.invoke(fieldValue, paras);
			if (accessible == false) {
				method.setAccessible(false);
			}
			return o;
		} catch (Throwable e) {
			if (e instanceof InvocationTargetException) {
				throw ((InvocationTargetException) e).getTargetException();
			} else {
				throw e;
			}
		}
	}

	public static Imposteriser imposteriser = ClassImposteriser.INSTANCE;

	/**
	 * 构造一个type类型的mock spring bean
	 * 
	 * @param <T>
	 * @param name
	 *            spring bean的名称
	 * @param type
	 *            spring bean的类型
	 * @return
	 */
	public static <T> T proxy(final Object testedObject, final Field field) {
		FieldProxy handler = new FieldProxy(testedObject, field.getName());
		Class type = field.getType();
		return (T) imposteriser.imposterise(handler, type);
	}

	/**
	 * 构造一个type类型的mock spring bean
	 * 
	 * @param <T>
	 * @param testedObject
	 * @param fieldName
	 * @return
	 */
	public static <T> T proxy(final Object testedObject, final String fieldName) {
		FieldProxy handler = new FieldProxy(testedObject, fieldName);
		Field field = FieldAccessor.getField(testedObject.getClass(), fieldName);
		Class type = field.getType();
		return (T) imposteriser.imposterise(handler, type);
	}
}
