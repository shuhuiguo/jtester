package org.jtester.module.jmockit.mockbug;

import org.apache.log4j.Logger;

public class SayHelloImpl2 {
	public static final Logger log = Logger.getLogger(SayHelloImpl2.class);

	public SayHelloImpl2() {
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
