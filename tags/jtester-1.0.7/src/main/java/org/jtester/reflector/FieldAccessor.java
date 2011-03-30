/**
 * Copyright � 2007 J2Speed. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.reflector;

import java.lang.reflect.Modifier;

/**
 * Allow to access a private field of a class.
 * 
 * @version 1.0
 * @since 0.1
 * @author Alessandro Nistico
 * 
 * @param <T>
 *            the type of the target type which hierarchy contains the field.
 * @param <V>
 *            the type of the field.
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
		} catch (Exception e) {
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
		} catch (Exception e) {
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
	public static Object getFieldValue(Class<?> claz, Object obj, String field) {
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
}
