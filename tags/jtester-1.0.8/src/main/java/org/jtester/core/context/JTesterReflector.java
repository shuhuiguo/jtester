package org.jtester.core.context;

import java.lang.reflect.Field;

import org.jtester.module.utils.FieldProxy;
import org.jtester.reflector.FieldAccessor;
import org.jtester.reflector.MethodAccessor;
import org.jtester.reflector.PropertiesAccessor;
import org.jtester.reflector.StaticFieldAccessor;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JTesterReflector {
	/**
	 * 调用类为clazz,名称为method的方法
	 * 
	 * @param methodName
	 * @param paras
	 * @return
	 */
	public <T> T invoke(Class clazz, Object target, String method, Object... paras) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessor.getProxiedObject(target);
		return (T) MethodAccessor.invoke(clazz, _target, method, paras);
	}

	public <T> T invoke(Object target, String method, Object... paras) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		if (target instanceof Class) {
			return (T) invokeStatic((Class) target, method, paras);
		} else {
			Object _target = PropertiesAccessor.getProxiedObject(target);
			return (T) invoke(_target.getClass(), _target, method, paras);
		}
	}

	public <T> T invokeStatic(Class target, String method, Object... paras) {
		return (T) MethodAccessor.invoke(target, null, method, paras);
	}

	public void setField(Object target, String field, Object value) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessor.getProxiedObject(target);
		setField(_target.getClass(), _target, field, value);
	}

	public void setField(Class clazz, Object target, String field, Object value) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessor.getProxiedObject(target);
		FieldAccessor accessor = new FieldAccessor(field, clazz);
		accessor.set(_target, value);
	}

	/**
	 * 返回target对象中名称为field的字段
	 * 
	 * @param <T>
	 *            字段类型
	 * @param target
	 *            字段所有者
	 * @param field
	 *            字段名称
	 * @return 字段值
	 */
	public <T> T getField(Object target, String field) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessor.getProxiedObject(target);
		return (T) getField(_target.getClass(), _target, field);
	}

	/**
	 * 返回target对象中名称为field的字段
	 * 
	 * @param clazz
	 *            定义字段的类（可能是target对象的父类或target自身)
	 * @param target
	 *            字段所有者实例
	 * @param field
	 *            字段值
	 * @return
	 */
	public <T> T getField(Class clazz, Object target, String field) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessor.getProxiedObject(target);
		FieldAccessor accessor = new FieldAccessor(field, clazz);
		return (T) accessor.get(_target);
	}

	public <T> T getStaticField(Class clazz, String field) {
		StaticFieldAccessor accessor = new StaticFieldAccessor(field, clazz);
		Object o = accessor.get();
		return (T) o;
	}

	public void setStaticField(Class clazz, String field, Object value) {
		StaticFieldAccessor accessor = new StaticFieldAccessor(field, clazz);
		accessor.set(value);
	}

	/**
	 * 返回spring代理的目标对象
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	public <T> T getSpringAdvisedTarget(Object source) {
		Object target = PropertiesAccessor.getProxiedObject(source);
		return (T) target;
	}

	/**
	 * 创建target对象field字段的代理实例<br>
	 * 用于运行时转移代理操作到字段对象上
	 * 
	 * @param <T>
	 * @param target
	 * @param field
	 * @return
	 */
	public <T> T createFieldProxy(Object target, String fieldname) {
		if (target == null) {
			throw new RuntimeException("can't get a field from null object.");
		}
		Field field = FieldAccessor.getField(target.getClass(), fieldname);
		Object proxy = FieldProxy.proxy(target, field);
		return (T) proxy;
	}
}
