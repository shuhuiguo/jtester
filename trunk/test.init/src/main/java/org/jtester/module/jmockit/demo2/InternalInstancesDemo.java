package org.jtester.module.jmockit.demo2;

import java.util.Observable;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.jtester.module.jmockit.demo2.Service.ServiceImpl;

@SuppressWarnings({ "rawtypes" })
public class InternalInstancesDemo {
	private final static Logger log4j = Logger.getLogger(InternalInstancesDemo.class);

	private final Service service1 = new ServiceImpl();
	private final Service service2 = new Service() {
		public int doSomething() {
			return 2;
		}
	};

	Observable observable;

	public int businessOperation(final boolean b) {
		Object o = new Callable() {
			public Object call() {
				throw new IllegalStateException();
			}
		}.call();
		log4j.info("callalbe call result:" + o);

		observable = new Observable() {
			{
				if (b) {
					throw new IllegalArgumentException();
				}
			}
		};

		return service1.doSomething() + service2.doSomething();
	}
}
