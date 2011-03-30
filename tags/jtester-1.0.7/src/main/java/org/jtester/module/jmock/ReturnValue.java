package org.jtester.module.jmock;

import java.io.File;
import java.util.Collection;

import org.jmock.Expectations;
import org.jtester.utility.ClazzUtil;
import org.jtester.utility.SerializeUtil;

@Deprecated
public class ReturnValue {
	private Expectations expectations;

	public ReturnValue(Expectations expectations) {
		this.expectations = expectations;
	}

	public void value(Object result) {
		expectations.will(Expectations.returnValue(result));
	}

	/**
	 * 行为（api）的返回值,(从指定的xml中反序列化回来)
	 * 
	 * @param returnClazz
	 *            要返回对象的类型
	 * @param xmlUrl
	 *            反序列化的xml文件名称(包含classpath路径)
	 */
	public void value(Class<?> returnClazz, String xmlUrl) {
		Object o = SerializeUtil.fromXML(returnClazz, xmlUrl);
		this.value(o);
	}

	/**
	 * 行为（api）的返回值,(从指定的xml中反序列化回来)
	 * 
	 * @param returnClazz
	 *            要返回对象的类型
	 * @param pathClazz
	 *            xml文件所在的class，用来方便定位xml文件路径
	 * @param xmlUrl
	 *            反序列化的xml文件名称
	 */
	public void value(Class<?> returnClazz, Class<?> pathClazz, String xmlUrl) {
		String path = ClazzUtil.getPathFromPath(pathClazz);
		Object o = SerializeUtil.fromXML(returnClazz, path + File.separatorChar + xmlUrl);
		this.value(o);
	}

	public void iterator(Collection<?> collection) {
		expectations.will(Expectations.returnIterator(collection));
	}

	public <T> void iterator(T... items) {
		expectations.will(Expectations.returnIterator(items));
	}

	public void enumeration(Collection<?> collection) {
		expectations.will(Expectations.returnEnumeration(collection));
	}

	public <T> void enumeration(T... items) {
		expectations.will(Expectations.returnEnumeration(items));
	}
}
