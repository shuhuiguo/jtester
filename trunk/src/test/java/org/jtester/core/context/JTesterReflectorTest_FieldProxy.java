package org.jtester.core.context;

import org.jtester.annotations.SpringInitMethod;
import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.jtester.unitils.spring.SpringBeanFor;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SuppressWarnings("unused")
@SpringApplicationContext
@AutoBeanInject
@Test(groups = "jtester")
public class JTesterReflectorTest_FieldProxy extends JTester {
	@SpringBeanByName
	ProxyBean proxyBean;

	@SpringBeanFor
	AutoBean autoBean = new AutoBean() {
		{
			want.object(proxyBean).isNull();
			this.proxyBean = reflector.getFieldProxy(JTesterReflectorTest_FieldProxy.this, "proxyBean");
		}
	};

	public void test_GetFieldProxy() {
		String result = this.autoBean.call();
		want.string(result).isEqualTo("call");
		ProxyBean proxy = this.autoBean.getProxyBean();
		want.object(proxy).not(the.object().same(this.proxyBean));
	}

	public static class AutoBean {
		ProxyBean proxyBean;

		public String call() {
			return this.proxyBean.call();
		}

		public ProxyBean getProxyBean() {
			return proxyBean;
		}
	}

	public static class ProxyBean {
		public String call() {
			return "call";
		}
	}
}
