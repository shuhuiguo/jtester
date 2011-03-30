package org.jtester.fit.spring;

import org.jtester.annotations.SpringBeanRemote;
import org.jtester.annotations.SpringBeanRemote.SpringBeanRemoteType;

public class RemoteInvokerRegisterTest_OnClientHelper {

	public static class DefaultRegister {
		@SpringBeanRemote
		Object bean;
	}

	public static class RegisterHessian {
		@SpringBeanRemote(value = "hessian/bean")
		Object bean;
	}

	public static class RegisterHttpInvoker {
		@SpringBeanRemote(value = "httpinvoker/bean")
		Object bean;
	}

	public static class RegisterBoth {
		@SpringBeanRemote(value = "bean", type = SpringBeanRemoteType.httpinvoker)
		Object bean1;

		@SpringBeanRemote(value = "bean")
		Object bean2;
	}
}
