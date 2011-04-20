package org.jtester.module.jmockit.mockbug;

import org.apache.log4j.Logger;
import org.jtester.utility.JTesterLogger;

public class SayHelloImpl {
	private final static Logger log4j = Logger.getLogger(SayHelloImpl.class);

	public SayHelloImpl() {
		JTesterLogger.mark("init log");
	}

	public String sayHello() {
		log4j.info("如果@Mock 一个实现类的第一次运行的时候,静态变量会被置为null,此处抛出NullPointerException");
		return "say hello:" + getName();
	}

	private String getName() {
		return "darui.wu";
	}

}
