package org.jtester.module.jmock;

import java.io.File;

import org.jmock.Expectations;
import org.jmock.api.Action;
import org.jtester.bytecode.reflector.helper.ClazzHelper;
import org.jtester.utility.SerializeUtil;

@Deprecated
@SuppressWarnings({ "rawtypes" })
public class ReturnExpections {
	public ReturnExpections(JTesterExpectations expectations) {
		this.expectations = expectations;
		this.returns = new ReturnValue(expectations);
	}

	private JTesterExpectations expectations;

	public ReturnValue returns;

	/**
	 * 希望行为发生中抛出异常
	 * 
	 * @param throwable
	 */
	public void throwException(Throwable throwable) {
		expectations.will(Expectations.throwException(throwable));
	}

	/**
	 * 希望行为发生中抛出异常,(异常的值从xml中反序列化回来)
	 * 
	 * @param exceptionClazz
	 *            异常的类型class
	 * @param xmlUrl
	 *            xml文件的url
	 */
	public void throwException(Class<? extends Throwable> exceptionClazz, String xmlUrl) {
		Throwable o = SerializeUtil.fromXML(exceptionClazz, xmlUrl);
		expectations.will(Expectations.throwException(o));
	}

	/**
	 * 希望行为发生中抛出异常,(异常的值从xml中反序列化回来)
	 * 
	 * @param exceptionClazz
	 *            异常的类型class
	 * @param pathClazz
	 *            xml文件所在路径的class，用来方便定位xml的classpath路径
	 * @param xmlname
	 *            xml文件的名称
	 */
	public void throwException(Class<? extends Throwable> exceptionClazz, Class pathClazz, String xmlname) {
		String xmlUrl = ClazzHelper.getPathFromPath(pathClazz) + File.separatorChar + xmlname;
		this.throwException(exceptionClazz, xmlUrl);
	}

	public void doAll(Action... actions) {
		expectations.will(Expectations.doAll(actions));
	}

	public void onConsecutiveCalls(Action... actions) {
		expectations.will(Expectations.onConsecutiveCalls(actions));
	}

	public void doAction(Action action) {
		expectations.will(action);
	}
}
