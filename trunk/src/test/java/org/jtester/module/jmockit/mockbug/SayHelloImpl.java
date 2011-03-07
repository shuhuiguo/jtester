package org.jtester.module.jmockit.mockbug;

import org.apache.log4j.Logger;

public class SayHelloImpl {
	protected static final Logger log = Logger.getLogger(SayHelloImpl.class);

	public SayHelloImpl() {
		System.out.println("init log");
	}

	public String sayHello() {
		log.info("如果@Mock 一个实现类的第一次运行的时候,静态变量会被置为null,此处抛出NullPointerException");
		return "say hello:" + getName();
	}

	private String getName() {
		return "darui.wu";
	}

}
