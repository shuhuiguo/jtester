package org.jtester.reflector;

import static java.util.Arrays.asList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import org.jtester.exception.ExceptionWrapper;
import org.jtester.exception.JTesterException;
import org.jtester.reflector.helper.ClazzHelper;

/**
 * Allow to access a private field of a class.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FieldAccessor<T, V> extends BaseFieldAccessor<T, V> {

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param type
	 *            the class from which to start searching the field
	 */
	public FieldAccessor(String fieldName, Class<? extends T> type) {
		super(fieldName, type);
		if (Modifier.isStatic(field.getModifiers())) {
			throw new IllegalArgumentException("Field " + fieldName + " is static");
		}
	}

	/**
	 * Gets the value of the field.
	 * 
	 * @param target
	 *            the instance from which to get the value.
	 * @return the current field value.
	 */
	public final V get(T target) {
		try {
			return (V) field.get(target);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the value for the field.
	 * 
	 * @param target
	 *            the target instance on which to set the new value.
	 * @param value
	 *            the value to set.
	 */
	public final void set(T target, V value) {
		try {
			field.set(target, value);
		} catch (RuntimeException e) {
			throw e;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置实例target的字段值
	 * 
	 * @param target
	 * @param field
	 * @param value
	 */
	public static void setFieldValue(Object target, String field, Object value) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null.");
		}
		FieldAccessor accessor = new FieldAccessor(field, target.getClass());
		accessor.set(target, value);
	}

	/**
	 * 获得对象obj的属性名为fieldname的字段值
	 * 
	 * @param claz
	 * @param obj
	 * @param field
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Object getFieldValue(Class claz, Object obj, String field) {
		FieldAccessor accessor = new FieldAccessor(field, claz);
		Object o = accessor.get(obj);
		return o;
	}

	/**
	 * 获得对象obj的属性名为fieldname的字段值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	public static Object getFieldValue(Object obj, String field) {
		if (obj == null) {
			throw new RuntimeException("the obj can't be null!");
		}
		Object o = getFieldValue(obj.getClass(), obj, field);
		return o;
	}

	/**
	 * Returns the value of the given field (may be private) in the given object
	 * 
	 * @param object
	 *            The object containing the field, null for static fields
	 * @param field
	 *            The field, not null
	 * @return The value of the given field in the given object
	 * @throws JTesterException
	 *             if the field could not be accessed
	 */
	public static <T> T getFieldValue(Object object, Field field) {
		try {
			field.setAccessible(true);
			return (T) field.get(object);

		} catch (IllegalArgumentException e) {
			throw new JTesterException("Error while trying to access field " + field, e);

		} catch (IllegalAccessException e) {
			throw new JTesterException("Error while trying to access field " + field, e);
		}
	}

	/**
	 * Sets the given value to the given field on the given object<br>
	 * 如果value对象是spring proxy对象，异常信息的消息作了一些包装，使提示更明显
	 * 
	 * @param object
	 *            The object containing the field, not null
	 * @param field
	 *            The field, not null
	 * @param value
	 *            The value for the given field in the given object
	 * @throws JTesterException
	 *             if the field could not be accessed
	 */
	public static void setFieldValue(Object object, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(object, value);

		} catch (IllegalArgumentException e) {
			Exception je = new JTesterException("Unable to assign the value to field: " + field.getName()
					+ ". Ensure that this field is of the correct type. Value: " + value, e);
			throw ExceptionWrapper.wrapdAdvisedException(value, je);
		} catch (IllegalAccessException e) {
			Exception je = new JTesterException("Error while trying to access field " + field, e);
			throw ExceptionWrapper.wrapdAdvisedException(value, je);
		}
	}

	/**
	 * 返回类所有的字段（包括父类的）<br>
	 * Gets all fields of the given class and all its super-classes.
	 * 
	 * @param clazz
	 *            The class
	 * @return The fields, not null
	 */
	public static Set<Field> getAllFields(Class clazz) {
		Set<Field> result = new HashSet<Field>();
		if (clazz == null || clazz.equals(Object.class)) {
			return result;
		}

		// add all fields of this class
		Field[] declaredFields = clazz.getDeclaredFields();
		result.addAll(asList(declaredFields));
		// add all fields of the super-classes
		result.addAll(getAllFields(clazz.getSuperclass()));
		return result;
	}

	/**
	 * Returns all declared fields of the given class that are assignable from
	 * the given type.
	 * 
	 * @param clazz
	 *            The class to get fields from, not null
	 * @param type
	 *            The type, not null
	 * @param isStatic
	 *            True if static fields are to be returned, false for non-static
	 * @return A list of Fields, empty list if none found
	 */
	public static Set<Field> getFieldsAssignableFrom(Class clazz, Type type) {
		Set<Field> fieldsOfType = new HashSet<Field>();
		Set<Field> allFields = getAllFields(clazz);
		for (Field field : allFields) {
			boolean isAssignFrom = ClazzHelper.isAssignable(type, field.getGenericType());
			if (isAssignFrom) {
				fieldsOfType.add(field);
			}
		}
		return fieldsOfType;
	}

	/**
	 * Returns the fields in the given class that have the exact given type. The
	 * class's superclasses are also investigated.
	 * 
	 * @param clazz
	 *            The class to get the field from, not null
	 * @param type
	 *            The type, not null
	 * @param isStatic
	 *            True if static fields are to be returned, false for non-static
	 * @return The fields with the given type
	 */
	public static Set<Field> getFieldsOfType(Class clazz, Type type) {
		Set<Field> fields = new HashSet<Field>();
		Set<Field> allFields = getAllFields(clazz);
		for (Field field : allFields) {
			boolean isTypeEquals = field.getType().equals(type);
			if (isTypeEquals) {
				fields.add(field);
			}
		}
		return fields;
	}
}
