package org.jtester.reflector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jtester.exception.JTesterException;
import org.jtester.utility.ArrayHelper;
import org.jtester.utility.ClazzUtil;

/**
 * POJO反射处理工具类
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReflectUtil {
	/**
	 * 给对象obj名为fieldname的属性赋值
	 * 
	 * @param obj
	 *            赋值对象
	 * @param fieldName
	 *            赋值属性
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		if (obj == null) {
			throw new RuntimeException("the obj can't be null!");
		}
		try {
			Field field = obj.getClass().getDeclaredField(fieldName);
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(accessible);
		} catch (Exception e) {
			String error = "Unable to update the value in field[" + fieldName + "]";
			throw new JTesterException(error, e);
		}
	}

	/**
	 * Sets the given value to the given field on the given object<br>
	 * 和 org.unitils.util.ReflectionUtils setFieldValue方法相比，
	 * 多了判断value对象是否是spring的proxy对象<br>
	 * 
	 * @param object
	 *            The object containing the field, not null
	 * @param field
	 *            The field, not null
	 * @param value
	 *            The value for the given field in the given object
	 * @throws UnitilsException
	 *             if the field could not be accessed
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		try {
			org.jtester.utility.ReflectionUtils.setFieldValue(object, field, value);
		} catch (Exception e) {
			throw decoratedAdvisedException(value, e);
		}
	}

	/**
	 * 包装一下异常信息的消息，使提示更明显
	 * 
	 * @param advised
	 * @param e
	 */
	public static RuntimeException decoratedAdvisedException(Object object, Exception e) {
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
			Class<?>[] clazzes = ((org.springframework.aop.framework.Advised) object).getProxiedInterfaces();
			sb.append("proxyed by the interfaces:\n");
			for (Class<?> clazz : clazzes) {
				sb.append("\t" + clazz.getName() + "\n");
			}
			return new RuntimeException(sb.toString(), e);
		}
	}

	public static Object getFieldValue(Object obj, Field field) {
		if (obj == null) {
			throw new RuntimeException("the obj can't be null!");
		}
		boolean accessible = field.isAccessible();
		try {
			field.setAccessible(true);
			Object o = field.get(obj);
			return o;
		} catch (Exception e) {
			throw new JTesterException("Unable to get the value in field[" + field.getName() + "]", e);
		} finally {
			field.setAccessible(accessible);
		}
	}

	/**
	 * 获得单值对象（非集合或数组,但包含Map）的单个属性值<br>
	 * 先访问get方法(is方法)，如果没有get方法再直接访问属性值
	 * 
	 * @param object
	 * @param ognlExpression
	 * @param throwException
	 * @return
	 */
	public static Object getPropertyValue(final Object object, String ognlExpression, boolean throwException) {
		String[] expressions = ognlExpression.split("\\.");
		Object target = object;
		for (String prop : expressions) {
			target = getProperty(target, prop);
		}
		return target;
	}

	/**
	 * * o 先根据get方法访问对象的属性，如果存在则返回<br>
	 * o 再根据is方法方法对象的属性，且方法值是bool型，返回<br>
	 * o 否则，直接方法对象的字段<br>
	 * o 如果对象是Map，则根据key值取
	 * 
	 * @param o
	 * @param prop
	 * @return
	 */
	public static Object getProperty(final Object o, String prop) {
		if (o == null) {
			throw new RuntimeException("can't get the property value from a null object.");
		}
		if (o instanceof Map) {
			Map map = (Map) o;
			return map.get(prop);
		}
		try {
			String method = ClazzUtil.camel("get", prop);
			MethodAccessor accessor = new MethodAccessor(o, method);
			return accessor.invoke();
		} catch (Exception e) {
			try {
				String method = ClazzUtil.camel("is", prop);
				MethodAccessor accessor = new MethodAccessor(o, method);
				Object b = accessor.invoke();
				if (b instanceof Boolean) {
					return b;
				}
				throw new RuntimeException();
			} catch (Exception e1) {
				Object o2 = FieldAccessor.getFieldValue(o, prop);
				return o2;
			}
		}
	}

	/**
	 * 获得单值对象（非集合或数组,但包含Map）的多个属性值
	 * 
	 * @param object
	 * @param ognlExpression
	 * @param throwException
	 * @return
	 */
	public static Object[] getPropertyValue(Object object, String[] ognlExpression, boolean throwException) {
		List<Object> os = new ArrayList<Object>();
		for (String ognl : ognlExpression) {
			Object value = getPropertyValue(object, ognl, throwException);
			os.add(value);
		}
		return os.toArray(new Object[0]);
	}

	/**
	 * 获得数组（集合)中各个对象的属性值列表<br>
	 * 
	 * @param arr
	 *            数组或集合,如果是单值(或Map)转为size=1的集合处理
	 * @param property
	 * @return
	 */
	public static List<?> getArrayItemProperty(Object arr, String property) {
		Collection coll = ArrayHelper.convertToCollectionWithoutMap(arr);
		List values = new ArrayList();
		for (Object o : coll) {
			Object value = ReflectUtil.getPropertyValue(o, property, false);
			values.add(value);
		}
		return values;
	}

	/**
	 * 获得对象的属性列表<br>
	 * o 如果对象是集合或数组，返回集合对象的属性值的列表<br>
	 * o 如果对象是单值，且属性是集合或数组，直接返回单值对象的属性值<br>
	 * o 如果对象是单值，且属性是非集合和数组类型，构造包含这个属性值的列表
	 * 
	 * @param item
	 * @param property
	 * @return
	 */
	public static Collection getArrayOrItemProperty(Object item, String property) {
		Collection values = null;
		if (ArrayHelper.isCollOrArray(item)) {
			values = ReflectUtil.getArrayItemProperty(item, property);
		} else {
			Object o = ReflectUtil.getProperty(item, property);
			values = ArrayHelper.convertToCollectionWithoutMap(o);
		}
		return values;
	}

	/**
	 * 获得数组（集合)中各个对象的多个属性值
	 * 
	 * @param arr
	 *            数组或集合,如果是单值(或Map)转为size=1的集合处理
	 * @param properties
	 * @return
	 */
	public static Object[][] getArrayItemProperties(Object arr, String[] properties) {
		Collection coll = ArrayHelper.convertToCollectionWithoutMap(arr);

		List values = new ArrayList();
		for (Object o : coll) {
			Object[] props = ReflectUtil.getPropertyValue(o, properties, false);
			values.add(props);
		}
		return (Object[][]) values.toArray(new Object[0][0]);
	}

	/**
	 * 如果是spring代理对象，获得被代理的对象
	 * 
	 * @param target
	 * @return
	 */
	public static Object getProxiedObject(Object target) {
		try {
			if (target instanceof org.springframework.aop.framework.Advised) {
				return ((org.springframework.aop.framework.Advised) target).getTargetSource().getTarget();
			} else {
				return target;
			}
		} catch (java.lang.NoClassDefFoundError error) {
			return target;
		} catch (Exception e) {
			throw new JTesterException(e);
		}
	}

	/**
	 * 调用类为clazz,名称为method的方法
	 * 
	 * @param methodName
	 * @param paras
	 * @return
	 */
	public static <T> T invoke(Class<?> clazz, Object target, String method, Object... paras) {
		List<Class<?>> paraClazz = new ArrayList<Class<?>>();
		if (paras != null) {
			for (Object para : paras) {
				paraClazz.add(para == null ? null : para.getClass());
			}
		}
		MethodAccessor accessor = new MethodAccessor(target, clazz, method, paraClazz.toArray(new Class<?>[0]));
		return (T) accessor.invoke(paras);
	}
}
