package org.jtester.spec.scenario;

import org.jtester.module.ICore.DataIterator;

/**
 * 场景文件解析错误时，返回一个错误原因的场景列表
 * 
 * @author darui.wudr 2012-7-12 下午4:57:01
 */
public final class ExceptionJSpecScenario extends JSpecScenario {
	private Exception exception;

	private ExceptionJSpecScenario(Throwable e) {
		this.scenario = "parse scenario error.";
		this.exception = new Exception(this.scenario, e);
	}

	@Override
	public void validate() throws Throwable {
		throw exception;
	}

	public static DataIterator iterator(Throwable e) {
		DataIterator it = new DataIterator();
		it.data(new ExceptionJSpecScenario(e));
		return it;
	}
}
