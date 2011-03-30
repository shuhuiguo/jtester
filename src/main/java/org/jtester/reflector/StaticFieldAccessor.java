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
 * Allow to access a private static field of a class.
 * 
 * @version 1.0
 * @since 0.8
 * @author Alessandro Nistico
 * 
 * @param <T>
 *            the type of the class containing the field.
 * @param <V>
 *            the type of the field.
 */
public class StaticFieldAccessor<T, V> extends BaseFieldAccessor<T, V> {

	/**
	 * Constructor.
	 * 
	 * @param fieldName
	 *            the field name
	 * @param type
	 *            the target from which to get the value
	 */
	public StaticFieldAccessor(String fieldName, Class<? extends T> type) {
		super(fieldName, type);
		if (!Modifier.isStatic(field.getModifiers())) {
			throw new IllegalArgumentException("Field " + fieldName + " is not static");
		}
		if (!field.getDeclaringClass().equals(type)) {
			throw new IllegalArgumentException("Field " + fieldName + " is not declared by the class " + type);
		}
	}

	/**
	 * Gets the value of the field.
	 * 
	 * @return the current field value
	 */
	@SuppressWarnings("unchecked")
	public final V get() {
		try {
			return (V) field.get(null);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the value for the field.
	 * 
	 * @param value
	 */
	public final void set(V value) {
		try {
			field.set(null, value);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
