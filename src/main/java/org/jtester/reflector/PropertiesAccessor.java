package org.jtester.reflector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jtester.exception.JTesterException;
import org.jtester.utility.ArrayHelper;
import org.jtester.utility.StringHelper;

/**
 * POJO属性值访问
 * 
 * @author darui.wudr
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertiesAccessor {
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
			String method = StringHelper.camel("get", prop);
			MethodAccessor accessor = new MethodAccessor(o, method);
			return accessor.invoke();
		} catch (Exception e) {
			try {
				String method = StringHelper.camel("is", prop);
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
			Object value = PropertiesAccessor.getPropertyValue(o, property, false);
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
			values = PropertiesAccessor.getArrayItemProperty(item, property);
		} else {
			Object o = PropertiesAccessor.getProperty(item, property);
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
			Object[] props = PropertiesAccessor.getPropertyValue(o, properties, false);
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

}
